package com.jap.controller.admin;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpRequest;
import com.blade.mvc.http.HttpResponse;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oidc.OidcConfig;
import com.fujieid.jap.oidc.OidcStrategy;
import com.jap.bladeAdapter.BladeRequestAdapter;
import com.jap.bladeAdapter.BladeResponseAdapter;
import com.jap.kit.RetKit;
import com.jap.service.admin.JapOidcUserServiceImpl;
import com.jap.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hq.W
 * @program JAP-demo
 * @description OidcController
 */
@Slf4j
@Path("/oidc")
public class OidcController {
    private static Logger logger = LoggerFactory.getLogger(OidcController.class);
    private static OidcConfig config = new OidcConfig();

    @Resource(name = "OIDC")
    @Inject
    private JapUserService japUserService = new JapOidcUserServiceImpl();
    private OidcStrategy oidcStrategy = new OidcStrategy(japUserService, new JapConfig());

    @PostRoute("/getData")
    public void getBaseData(RouteContext routeContext){
//        获取参数，这里是为了方便测试者以页面通用的方式进行输入，而不是修改源代码。但参数clientSecret等一般很重要，实际开发下不会这样使用
        String clientId = routeContext.query("clientId");
        String clientSecrect = routeContext.query("clientSecret");
        String redirectURI = routeContext.query("redirectURI");

        // 配置 OIDC 的 Issue 链接
        config.setIssuer("https://oauth.aliyun.com")
                .setPlatform("aliyun")
                .setState(UuidUtils.getUUID())
                .setClientId(clientId)
                .setClientSecret(clientSecrect)
                .setCallbackUrl(redirectURI)
                .setScopes(new String[]{"aliuid","openid","profile"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code);

        logger.info("拿到参数，开始准备重定向授权页面");
        this.renderAuth(routeContext);

    }
    /**
     * <a href="https://justauth.plus/quickstart/jap-oidc.html#%E6%B7%BB%E5%8A%A0%E4%BE%9D%E8%B5%96">jap-oidc 是为了方便快速的集成所有支持标准 OIDC 协议的平台而添加的增强包。</a><br>
     * 准备策略：<br>
     * ----OidcStrategy：选择oauth验证策略，加入已经实现该策略验证方式的service具体类，这里是JapOauth2UserServiceImpl,放进AbstractJapStrategy类(下一步认证需要使用），并初始化new config：用于设置是否是单点sso登录（默认为FALSE）、缓存和token有效时间，默认失效均时间为7天，AbstractJapStrategy中添加新的JapLocalCache缓存：单点登录——SsoJapUserStore缓存，否则SessionJapUserStore缓存,<br>
     * 最终一并放进JapAuthentication.setContext(japUserStore,japCache,japConfig)的context。<br>
     * <br>
     * 验证授权：<br>
     * ----oauth策略的验证方法是Oauth2Strategy的authenticate实现，但需要将issuer的相关信息进行设置后，转为OAuthConfig，才会走OAuth流程。首先，第一个参数也就是上一步配置的OidcConfig，authenticate中先需要进行一些列的check检查：
     * <li>1.checkAuthenticateConfig：检查OidcConfig是否为空，若为空，抛出MISS_AUTHENTICATE_CONFIG异常，以及上一步函数调用的参数类型是否正确</li>
     * <li>2.判断oidcConfig的Issuer是否为空，为空抛出MISS_ISSUER错误</li>
     * OIDC模式，通过拼接issuer拿到discoveryCacheKey，判断当前的japContext中是否包含通过拼接issuer拿到discoveryCacheKey键，此时两个方向：第一种，从japCache缓存中拿到discoveryCacheKey对应的OidcDiscoveryDto对象<br>
     * 第二种（第一次登录）：japCache中找不到对应值，则去HttpUtil工具类中发起以issuer拼接上“/.well-known/openid-configuration”为地址的get请求，进一步得到response的信息，最终返回OidcDiscoveryDto对象。<br>
     * 返回到到OidcStrategy，并将其加入japCache中后，走OAuth配置流程，向oidcConfig对象中加入OidcDiscoveryDto对象的授权接口，token接口和用户信息接口，<br>
     * 将oidcCOnfig的配置内容通过BeanUtil.copyProperties，得到OAuthConcig的配置，后面就完全走OAuth的authenticate流程 。
     */
    @Route("/auth")
    public void renderAuth(RouteContext routeContext) {

//        HttpServletRequest request = HttpUtils.toHttpServletReq((HttpRequest) routeContext.request());
//        HttpServletResponse response = HttpUtils.toHttpServletRes((HttpResponse) routeContext.response());
        HttpServletRequest request = new BladeRequestAdapter((HttpRequest) routeContext.request());
        HttpServletResponse response = new BladeResponseAdapter((HttpResponse) routeContext.response());

        JapResponse japResponse = oidcStrategy.authenticate(config, request,response);

        if (!japResponse.isSuccess()) {
            System.out.println(japResponse.getMessage());
            routeContext.json(RetKit.fail("msg","未授权"));
        }

        if (japResponse.isRedirectUrl()) {
            routeContext.json(RetKit.ok("toAuth",(String) japResponse.getData()));
            logger.info("授权成功");
        } else {
            JapUser japUser = (JapUser) japResponse.getData();

            Map<String,String> userInfos = new HashMap<>();
            logger.info(userInfos.toString());
            userInfos.put("token",japUser.getToken());
            userInfos.put("username",japUser.getUsername());
            userInfos.put("userId",japUser.getUserId());
            userInfos.put("password",japUser.getPassword());

            routeContext.json(RetKit.ok("userInfos",userInfos));
        }
        System.out.println(japResponse.getData());
    }
}
