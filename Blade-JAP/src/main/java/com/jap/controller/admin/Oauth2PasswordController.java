package com.jap.controller.admin;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.http.HttpRequest;
import com.blade.mvc.http.HttpResponse;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2EndpointMethodType;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2Strategy;
import com.jap.bladeAdapter.BladeRequestAdapter;
import com.jap.bladeAdapter.BladeResponseAdapter;
import com.jap.kit.RetKit;
import com.jap.service.admin.JapOauth2UserServiceImpl;
import com.xkcoding.json.JsonUtil;
import com.xkcoding.json.util.Kv;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description Oauth2PasswordController
 */
@Slf4j
@Path("/oauth")
public class Oauth2PasswordController {

    private static Logger logger = LoggerFactory.getLogger(Oauth2PasswordController.class);

    @Resource(name = "oauth")
    @Inject
    private JapUserService japUserService = new JapOauth2UserServiceImpl();

    private static OAuthConfig config = new OAuthConfig();
    private Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig());

    @PostRoute("/password/getData")
    public void getBaseData(RouteContext routeContext) throws IllegalAccessException {
//        获取参数，这里是为了方便测试者以页面通用的方式进行输入，而不是修改源代码。但参数clientSecret等一般很重要，实际开发下不会这样使用
        String clientId = routeContext.query("clientId");
        String clientSecrect = routeContext.query("clientSecret");
        String redirectURI = routeContext.query("redirectURI");
        String username = routeContext.query("username");
        String password = routeContext.query("password");
        System.out.println("\n------------------------------------------------------------------------------------------------------\n\t"+
                "your information:\t" +
                "\n\tusername: \t\t\t\t"+ username +
                "\n\tpassword:\t\t\t\t" + password);

        config.setPlatform("gitee")
                .setState(UuidUtils.getUUID())
                .setClientId(clientId)
                .setClientSecret(clientSecrect)
                .setCallbackUrl(redirectURI)
                .setTokenUrl("https://gitee.com/oauth/token")
                .setScopes(new String[]{"user_info"})
                .setGrantType(Oauth2GrantType.password)
                .setUserinfoUrl("https://gitee.com/api/v5/user")
                .setUserInfoEndpointMethodType(Oauth2EndpointMethodType.GET)
                .setUsername(username)
                .setPassword(password);

        System.out.println("\tredirectURI:\t\t\t"+redirectURI);
        this.renderAuth(routeContext);
    }
    /**
     * 准备策略：<br>
     * ----Oauth2Strategy：选择oauth验证策略，加入已经实现该策略验证方式的service具体类，这里是JapOauth2UserServiceImpl,放进AbstractJapStrategy类(下一步认证需要使用），并初始化new config：用于设置是否是单点sso登录（默认为FALSE）、缓存和token有效时间，默认失效均时间为7天，AbstractJapStrategy中添加新的JapLocalCache缓存：单点登录——SsoJapUserStore缓存，否则SessionJapUserStore缓存,<br>
     * 最终一并放进JapAuthentication.setContext(japUserStore,japCache,japConfig)的context。<br>
     * <br>
     * 验证授权：<br>
     * ----oauth策略的验证方法是Oauth2Strategy的authenticate实现，首先，第一个参数也就是上一步配置的OAuthConfig，authenticate中先需要进行一些列的check检查：
     * <li>1.checkOauthCallbackRequest：检查请求地址中有无错误的参数pram，若存在，则抛出apOauth2Exception异常，反之进行下一步操作</li>
     * <li>2.checkSession：检查session中是否已经保存过该用户，也就是判断之前用户已经登录并验证成功时保存在UserStore中，若存在，则把已经存进UserStore()的用户拿出，直接响应success，设置状态码200，反之进行下一步</li>
     * <li>3.checkAuthenticateConfig：检查第一个参数OAuthConfig的基类AuthenticateConfig配置是否为空等，若存在，抛出JapException:MISS_AUTHENTICATE_CONFIG异常等，反之，进行下一步</li>
     * <li>4.checkOauthConfig：进一步检查参数OAuthConfig对象的相关配置是否正确，如授权类型GrantType等，配置不正确，则抛出相关异常，全部配置正常，则进行下一步操作</li>
     * <br>
     * password模式，指定账号密码进行。在AccessTokenHelper类中，判断授权类型GrantType = password，调用getAccessTokenOfPasswordMode，从config中拿到相关参数，加入params集合，调用HttpUtils携带参数和URL，发出get请求，最终并返回AccessToken对象
     */
    @GetRoute("/password")
    public void renderAuth(RouteContext routeContext){

//        HttpServletRequest request = HttpUtils.toHttpServletReq((HttpRequest) routeContext.request());
//        HttpServletResponse response = HttpUtils.toHttpServletRes((HttpResponse) routeContext.response());
        HttpServletRequest request = new BladeRequestAdapter((HttpRequest) routeContext.request());
        HttpServletResponse response = new BladeResponseAdapter((HttpResponse) routeContext.response());

        JapResponse japResponse = oauth2Strategy.authenticate(config,request,response);
        if (!japResponse.isSuccess()) {
            routeContext.json(RetKit.fail("msg","未授权"));
        }
        if (japResponse.isRedirectUrl()) {
            //判断japResponse的data是否有一个以http开头的URL(回调地址)：((String)data).startsWith("http")

            System.out.println("isRedirectUrl1："+(String) japResponse.getData());
            logger.info("授权成功");

            routeContext.json(RetKit.ok("toAuth",(String)japResponse.getData()));
        } else {

            JapUser userInfos = (JapUser) japResponse.getData();
            //获取Additional的所有信息，（由object类型转为map）
            String ad = JsonUtil.toJsonString(userInfos.getAdditional());
            Kv parseKv = JsonUtil.parseKv(ad);

            System.out.println("\n\ttoken:\t\t\t\t\t" + userInfos.getToken()+
                    "\n\tcreated date:\t\t\t" + parseKv.get("created_at")+
                    "\n\tgists_url:\t\t\t\t" + parseKv.get("gists_url")+
                    "\n------------------------------------------------------------------------------------------------------");
            routeContext.json(RetKit.ok("userInfos",userInfos));
        }
    }
}
