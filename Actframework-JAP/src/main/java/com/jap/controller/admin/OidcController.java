package com.jap.controller.admin;

import act.app.ActionContext;
import act.controller.Controller;
import act.controller.annotation.UrlContext;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.oauth2.OAuthConfig;
import com.fujieid.jap.oauth2.Oauth2GrantType;
import com.fujieid.jap.oauth2.Oauth2ResponseType;
import com.fujieid.jap.oauth2.Oauth2Strategy;
import com.fujieid.jap.oidc.OidcConfig;
import com.fujieid.jap.oidc.OidcStrategy;
import com.jap.kit.RetKit;
import com.jap.service.JapOidcUserServiceImpl;
import com.jap.utils.HttpUtils;
import me.zhyd.oauth.utils.UuidUtils;
import org.osgl.logging.LogManager;
import org.osgl.logging.Logger;
import org.osgl.mvc.annotation.Action;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hq.W
 * @program JAP-demo
 * @description -OidcController
 */
@UrlContext("/oidc")
public class OidcController extends Controller.Util {

    private static Logger logger = LogManager.get(OidcController.class);

    @Inject
    @Singleton
    private JapOidcUserServiceImpl japOidcUserService = new JapOidcUserServiceImpl();

    private static OidcConfig config = new OidcConfig();
    private OidcStrategy oidcStrategy = new OidcStrategy(japOidcUserService,new JapConfig());

    @Action({"auth","getData"})
    public RetKit renderAuth(ActionContext context){

        Map<String, String[]> params = context.allParams();
        String[] clientIds = params.get("clientId");
        String[] clientSecrets = params.get("clientSecret");
        String[] redirectURIS = params.get("redirectURI");
        if (clientIds != null && clientSecrets != null && redirectURIS != null){
            // 配置 OIDC 的 Issue 链接
            config.setIssuer("https://oauth.aliyun.com")
                    .setPlatform("aliyun")
                    .setState(UuidUtils.getUUID())
                    .setClientId(clientIds[0])
                    .setClientSecret(clientSecrets[0])
                    .setCallbackUrl(redirectURIS[0])
                    .setScopes(new String[]{"aliuid","openid","profile"})
                    .setResponseType(Oauth2ResponseType.code)
                    .setGrantType(Oauth2GrantType.authorization_code);

            System.out.println("重定向地址："+redirectURIS[0]);
            logger.info("拿到参数，开始准备重定向授权页面");
        }

        HttpServletRequest request = HttpUtils.toHttpServletConReq(context);
        HttpServletResponse response = HttpUtils.toHttpServletConRes(context);
        JapResponse japResponse = oidcStrategy.authenticate(config, request,response);

        if (!japResponse.isSuccess()) {
            logger.info("授权失败：%s。", japResponse.getData());
            context.renderArg("响应失败。");
        }
        if (japResponse.isRedirectUrl()) {
            System.out.println("isRedirectUrl1："+japResponse.getData());
            logger.info("授权成功");
            return RetKit.ok("toAuth",japResponse.getData());
        }else {
            JapUser japUser = (JapUser) japResponse.getData();

            Map<String,String> userInfos = new HashMap<>();
            logger.info(userInfos.toString());
            userInfos.put("token",japUser.getToken());
            userInfos.put("username",japUser.getUsername());
            userInfos.put("userId",japUser.getUserId());
            userInfos.put("password",japUser.getPassword());
             logger.info("显示用户信息");

            return   RetKit.ok("userInfos",userInfos);
        }
    }
}
