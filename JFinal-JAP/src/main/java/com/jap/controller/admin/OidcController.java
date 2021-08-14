package com.jap.controller.admin;

import cn.hutool.core.util.URLUtil;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oidc.OidcConfig;
import com.fujieid.jap.oidc.OidcStrategy;
import com.jap.interceptor.JapInterceptor;
import com.jap.service.admin.JapOauth2UserServiceImpl;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import me.zhyd.oauth.utils.UuidUtils;

import java.io.IOException;

/**
 * @author hq.W
 * @program JAP-demo
 * @description OidcController
 */
@Before(JapInterceptor.class)
public class OidcController extends Controller {

    @Inject(JapOauth2UserServiceImpl.class)
    private JapUserService japUserService;

    @ActionKey("/oidc")
    @Clear
    public void index() throws IOException {
//        render("/templates/toLogin.html");
//        this.getResponse().sendRedirect.
               render("/toLogin.html");
    }
    /**
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
    @ActionKey("/authOIDC")
    @Clear
    public void renderAuth() {
        OidcStrategy oidcStrategy = new OidcStrategy(japUserService, new JapConfig());
        OidcConfig config = new OidcConfig();
        // 配置 OIDC 的 Issue 链接
        config.setIssuer("http://127.0.0.1:8091/")
                .setPlatform("gitee")
                .setState(UuidUtils.getUUID())
                .setClientId("12e2f9e10829d3da70739e4e8b83c747b4ac6d78e08693bbce990ba9c2063e2a")
                .setClientSecret("45a0c6b80c87358eb4bab3fe46758e5a3aebd00d8a48def4c803cf12f45b388d")
                .setCallbackUrl("http://127.0.0.1:8091/index")
                .setScopes(new String[]{"user_info"})
                .setResponseType(Oauth2ResponseType.code)
                .setGrantType(Oauth2GrantType.authorization_code);
        JapResponse japResponse = oidcStrategy.authenticate(config, this.getRequest(), this.getResponse());
        if (!japResponse.isSuccess()) {
            System.out.println(japResponse.getMessage());

            renderText("/?error=" + URLUtil.encode(japResponse.getMessage()));
        }
        if (japResponse.isRedirectUrl()) {
            renderText((String) japResponse.getData());
        } else {
            System.out.println(japResponse.getData());
            render("/template/index.html");
        }
    }
}
