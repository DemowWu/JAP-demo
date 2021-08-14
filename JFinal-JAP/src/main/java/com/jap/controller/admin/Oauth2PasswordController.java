package com.jap.controller.admin;

import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oauth2.Oauth2Strategy;
import com.jap.service.admin.JapOauth2UserServiceImpl;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import me.zhyd.oauth.utils.UuidUtils;

import javax.annotation.Resource;
import javax.sound.sampled.Control;

/**
 * @author hq.W
 * @program JAP-demo
 * @description Oauth2PasswordController
 */
public class Oauth2PasswordController extends Controller {

    @Resource(name = "oauth2")
    @Inject(JapOauth2UserServiceImpl.class)
    private JapUserService japUserService;

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
    @ActionKey("/oauth2Password")
    public void renderAuth(){
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService,new JapConfig());
        OAuthConfig config = new OAuthConfig();
        config.setPlatform("gitee")
                .setState(UuidUtils.getUUID())
                .setClientId("12e2f9e10829d3da70739e4e8b83c747b4ac6d78e08693bbce990ba9c2063e2a")
                .setClientSecret("45a0c6b80c87358eb4bab3fe46758e5a3aebd00d8a48def4c803cf12f45b388d")
                .setCallbackUrl("http://127.0.0.1:8091/oauth2Password")
                .setTokenUrl("https://gitee.com/oauth/token")
                .setScopes(new String[]{"user_info"})
                .setGrantType(Oauth2GrantType.password)
                .setUserinfoUrl("https://gitee.com/api/v5/user")
                .setUsername("jap2")
                .setPassword("jap2");
        JapResponse japResponse = oauth2Strategy.authenticate(config,this.getRequest(),this.getResponse());
        if (!japResponse.isSuccess()) {
            renderJson("/?error=" + URLUtil.encode(japResponse.getMessage()));
        }
        if (japResponse.isRedirectUrl()) {
            //判断japResponse的data是否有一个以http开头的URL：((String)data).startsWith("http")
            renderText("isRedirectUrl——————>" + (String) japResponse.getData());
        } else {
            JapUser data = (JapUser) japResponse.getData();
            renderText("登录成功\n" + "username：" + data.getUsername() + "\npassword：" + data.getPassword() + "\ntoken：" + data.getToken() + "\nuserId：" + data.getUserId());
        }
    }


}
