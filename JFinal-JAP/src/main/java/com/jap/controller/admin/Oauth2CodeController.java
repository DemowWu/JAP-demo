package com.jap.controller.admin;

import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;

import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.*;
import com.fujieid.jap.social.SocialConfig;
import com.fujieid.jap.social.SocialStrategy;
import com.jap.service.admin.JapOauth2UserServiceImpl;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.utils.UuidUtils;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author hq.W
 * @program JAP-demo
 * @description Oauth2控制器-Oauth2Controller
 */
public class Oauth2CodeController extends Controller {

    @Resource(name = "oauth2")
    @Inject(JapOauth2UserServiceImpl.class)
    private JapUserService japUserService;

//    初始化登录页面
    @ActionKey("/oauth")
    public void index() {
        render("/templates/oauth2Page/loginpage.html");
    }
    @ActionKey("/toAuth")
    public void toAuth(){
        redirect("https://gitee.com/oauth/authorize?client_id=12e2f9e10829d3da70739e4e8b83c747b4ac6d78e08693bbce990ba9c2063e2a&redirect_uri=http://127.0.0.1:8091/callback&response_type=code&state=111");
    }
    /**
    * 准备策略：<br>
    * ----Oauth2Strategy：选择oauth验证策略，加入已经实现该策略验证方式的service具体类，这里是JapOauth2UserServiceImpl,放进AbstractJapStrategy类(下一步认证需要使用），并初始化new config：用于设置是否是单点sso登录（默认为FALSE）、缓存和token有效时间，默认失效均时间为7天，AbstractJapStrategy中添加新的JapLocalCache缓存：单点登录——SsoJapUserStore缓存，否则SessionJapUserStore缓存,<br>
    * 最终一并放进JapAuthentication.setContext(japUserStore,japCache,japConfig)的context。<br>
    * <br>
    * ----注：在准备开始authenticate时，这里的demo是需要对OAuthConfig先进行相关配置:客户端ID，secret和回调地址等。另外，OAuth2.0不同的授权模式选择，这里以authorization_code为例。<br>
    * <br>
    * 验证授权：<br>
    * ----oauth策略的验证方法是Oauth2Strategy的authenticate实现，首先，第一个参数也就是上一步配置的OAuthConfig，authenticate中先需要进行一些列的check检查：
    * <li>1.checkOauthCallbackRequest：检查请求地址中有无错误的参数pram，若存在，则抛出apOauth2Exception异常，反之进行下一步操作</li>
    * <li>2.checkSession：检查session中是否已经保存过该用户，也就是判断之前用户已经登录并验证成功时保存在UserStore中，若存在，则把已经存进UserStore()的用户拿出，直接响应success，设置状态码200，反之进行下一步</li>
    * <li>3.checkAuthenticateConfig：检查第一个参数OAuthConfig的基类AuthenticateConfig配置是否为空等，若存在，抛出JapException:MISS_AUTHENTICATE_CONFIG异常等，反之，进行下一步</li>
    * <li>4.checkOauthConfig：进一步检查参数OAuthConfig对象的相关配置是否正确，如授权类型GrantType等，配置不正确，则抛出相关异常，全部配置正常，则进行下一步操作</li>
    * <br>
    * 当所有检查均是正确，则进入授权Authorization阶段：<br>
    * ————此时有两个方向，<br>
    * 第一种code进行加密：callback的携带参数响应码code或者令牌access_token为空且OAuth2的授权模式是授权码code模式或者简化implicit模式，则是进行pkce授权码模式，随机生成一串字符并用URL-Safe的Base64编码处理，用作 code_verifier ，将code_verifier使用SHA256哈希，并用URL-Safe的Base64编码处理，用作code_challenge，携带ode_challenge去认证服务器，拿到Code，则下一步换取token需要携带code_verifier,进行后则响应成功，设置状态码200，并进行拼接授权地址保存进响应的data中<br>
    * 第二种常规授权模式：<br>
    * <li>判断授权类型是code模式，进入getAccessTokenOfAuthorizationCodeMode ，先拿到参数code再凭code拿到token</li>
    * <li>由token去得到资源服务器的用户信息</li>
    * <br>
    * 用户信息拿到，登录成功，设置响应码200，并把该用户存储到UserStore。
    */
//注：oauth的几种模式，demo存在问题：拿不到token，待解决
    @ActionKey("/auth2Code")
    public void renderAuth() {
        Oauth2Strategy oauth2Strategy = new Oauth2Strategy(japUserService, new JapConfig());
        OAuthConfig config = new OAuthConfig();
        config.setPlatform("gitee")
                .setState(UuidUtils.getUUID())
                .setClientId("12e2f9e10829d3da70739e4e8b83c747b4ac6d78e08693bbce990ba9c2063e2a")
                .setClientSecret("45a0c6b80c87358eb4bab3fe46758e5a3aebd00d8a48def4c803cf12f45b388d")
                .setCallbackUrl("http://127.0.0.1:8091/auth2Code")
                .setAuthorizationUrl("https://gitee.com/oauth/authoriz")
                .setTokenUrl("https://gitee.com/oauth/token")
                .setUserinfoUrl("xx")
                .setScopes(new String[]{"user_info"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code);
        JapResponse japResponse = oauth2Strategy.authenticate(config, this.getRequest(), this.getResponse());
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
        /*OAuthConfig config = new OAuthConfig();
        config.setVerifyState(false);
        config.setPlatform("gitee")
                .setState("111")
                .setEnablePkce(false)
                .setClientId("12e2f9e10829d3da70739e4e8b83c747b4ac6d78e08693bbce990ba9c2063e2a")
                .setClientSecret("45a0c6b80c87358eb4bab3fe46758e5a3aebd00d8a48def4c803cf12f45b388d")
                .setCallbackUrl("http://127.0.0.1:8091/callback")
                .setAuthorizationUrl("https://gitee.com/oauth/authorize")
                .setTokenUrl("https://gitee.com/oauth/toke")
                .setUserinfoUrl("https://gitee.com/api/v5/user")
                .setScopes(new String[]{"user_info"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code);
        //authenticate先拿到token，由token去取userInfo
        JapResponse japResponse = oauth2Strategy.authenticate(config,this.getRequest(), this.getResponse());

        if(!japResponse.isSuccess()){
            renderText("URLUtil---------》"+URLUtil.encode(japResponse.getMessage()));
        }
        if (japResponse.isRedirectUrl()){
            render("/");
        }
        else {
            JapUser data = (JapUser) japResponse.getData();
            renderText("\n登录成功，响应码：code = " + japResponse.getCode()+"\nisSuccess = "+
                    japResponse.isSuccess()+"\nname = "+data.getUsername()+data.getToken());
        }
        return null;
    }*/

