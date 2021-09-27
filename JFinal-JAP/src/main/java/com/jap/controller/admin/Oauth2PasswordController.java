package com.jap.controller.admin;

import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.*;
import com.jap.kit.RetKit;
import com.jap.service.admin.JapOauth2UserServiceImpl;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.xkcoding.json.JsonUtil;
import com.xkcoding.json.util.Kv;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
/**
 * @author hq.W
 * @program JAP-demo
 * @description Oauth2PasswordController
 */
@Slf4j
public class Oauth2PasswordController extends Controller {

    private static Logger logger = LoggerFactory.getLogger(Oauth2PasswordController.class);
    @Resource(name = "oauth2")
    @Inject(JapOauth2UserServiceImpl.class)
    private JapOauth2UserServiceImpl japUserService = new JapOauth2UserServiceImpl();

    private static OAuthConfig config = new OAuthConfig();
    private  Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig());

    @ActionKey("/oauth/password/getData")
    public void getBaseData() throws IllegalAccessException {
//        获取参数，这里是为了方便测试者以页面通用的方式进行输入，而不是修改源代码。但参数clientSecret等一般很重要，实际开发下不会这样使用
        String clientId = this.getRequest().getParameter("clientId");
        String clientSecrect = this.getRequest().getParameter("clientSecret");
        String redirectURI = this.getRequest().getParameter("redirectURI");
        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");
        System.out.println("\n------------------------------------------------------------------------------------------------------\n\t"+
                "your information:\t");
        System.out.println("\tusername: \t\t\t\t"+ username +
                           "\n\tpassword:\t\t\t\t" + password );

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
        logger.info("拿到参数，开始准备重定向授权页面");

        this.renderAuth();
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
    @ActionKey("/oauth/password")
    public void renderAuth(){

        JapResponse japResponse = oauth2Strategy.authenticate(config,this.getRequest(),this.getResponse());
        if (!japResponse.isSuccess()) {
            renderJson("/?error=" + URLUtil.encode(japResponse.getMessage()));
        }
        if (japResponse.isRedirectUrl()) {
            //判断japResponse的data是否有一个以http开头的URL：((String)data).startsWith("http")
            renderText("isRedirectUrl——————>" + (String) japResponse.getData());
        } else {

            JapUser japUser = (JapUser) japResponse.getData();

            //获取Additional的所有信息，（由object类型转为map）
            String ad = JsonUtil.toJsonString(japUser.getAdditional());
            Kv parseKv = JsonUtil.parseKv(ad);

            System.out.println("\n\ttoken:\t\t\t\t\t" + japUser.getToken()+
                                "\n\tcreated date:\t\t\t" + parseKv.get("created_at")+
                                "\n\tgists_url:\t\t\t\t" + parseKv.get("gists_url")+
                    "\n------------------------------------------------------------------------------------------------------");
            renderJson(RetKit.ok("userInfos",japUser));
        }
    }


}
