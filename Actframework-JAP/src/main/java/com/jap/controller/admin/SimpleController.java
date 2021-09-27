package com.jap.controller.admin;

import act.app.ActionContext;
import act.controller.Controller;
import act.controller.annotation.UrlContext;
import act.view.RenderTemplate;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.simple.SimpleConfig;
import com.fujieid.jap.simple.SimpleStrategy;
import com.jap.kit.RetKit;
import com.jap.service.JapSimpleUserServiceImpl;
import com.jap.utils.HttpUtils;
import org.osgl.logging.LogManager;
import org.osgl.logging.Logger;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.result.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author hq.W
 * @program JAP-demo
 * @description SimpleController
 */

@UrlContext("/simple")
public class SimpleController extends Controller.Util{

    private static Logger logger = LogManager.get(SimpleController.class);

    @Inject
    @Singleton
    private JapSimpleUserServiceImpl japSimpleUserService = new JapSimpleUserServiceImpl();

    @GetAction("test")
    public Result toLogin(ActionContext context){
        String name = context.req().paramVal("name");
        if (null == name) {
            name = "TestName";
        }
        context.renderArg("name",name);
        return RenderTemplate.get();
    }

    /**准备策略：<br>
     * ----SimpleStrategy：选择simple验证策略，加入已经实现该策略验证方式的service具体类，这里是JapSimpleUserServiceImpl,放进AbstractJapStrategy类(下一步认证需要使用），并初始化new config：用于设置是否是单点sso登录（默认为FALSE）、缓存和token有效时间，默认失效均时间为7天，AbstractJapStrategy中添加新的缓存JapLocalCache：单点登录——SsoJapUserStore缓存，否则SessionJapUserStore缓存，
     * 最终一并放进JapAuthentication.setContext(japUserStore,japCache,japConfig)的context。<br>
     * ----注：在准备开始authenticate时，这里的demo是对simpleConfig保持默认的配置。<br>
     * 验证授权：<br>
     * ----simple策略的验证方法是SimpleStrategy的authenticate实现，首先是参数SimpleConfig验证配置的初始化，经过一系列配置检查后，检查checkSessionAndCookie中有无此时登录的用户，如果存在，则说明登录成功，设置状态码200并返回响应<br>
     * 如果不存在(说明是第一次登录)，就先去doResolveCredential：判断username和password是否同时非空，满足则封装为一个Credential并初始化该类变量，其中的rememberMe从request的getParam("rememberMe")函数进行判断，1——>Credential.getrememberMe()为真，先使用使用service具体类的getByName方法，未查到该用户账号，响应用户不存在NOT_EXIST_USER，否则继续使用validPassword，判断密码是否有效,
     * 密码也正确，此时两个方向判断：先判断有没有实现rememberMe ——有——>测需要先addCookie，再跳转loginSuccess，设置状态码200，否则，直接跳转loginSuccess。
     */
    @PostAction("logined")
    public RetKit get(ActionContext context) {
        Map<String, String[]> params = context.allParams();
        String[] username = params.get("username");
        String[] password = params.get("password");

        System.out.println(username[0] + "  " + password[0]);

        HttpServletRequest request = HttpUtils.toHttpServletConReq(context);
        HttpServletResponse response = HttpUtils.toHttpServletConRes(context);

        SimpleStrategy simpleStrategy = new SimpleStrategy(japSimpleUserService, new JapConfig());
        JapResponse japResponse = simpleStrategy.authenticate(new SimpleConfig(), request, response);

        if (!japResponse.isSuccess()) {
            logger.info("授权失败：%s。", japResponse.getData());
            context.renderArg("响应失败。");
        }
        JapUser userInfos = new JapUser();
        if (japResponse.isRedirectUrl()) {
            logger.info("进行授权，重定向。");
            //判断japResponse的data是否有一个以http开头的URL(回调地址)：((String)data).startsWith("http")
            return  RetKit.ok("toAuth", (String) japResponse.getData());
        } else {
            logger.info("获取已授权用户信息。");
            JapUser data = (JapUser) japResponse.getData();
            userInfos.setUserId(data.getUserId());
            userInfos.setUsername(data.getUsername());
            userInfos.setPassword(data.getPassword());
            userInfos.setToken(data.getToken());

            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------\n\t" +
                            "your information:\n\t" +
                            "userId: \t\t\t\t\t" + userInfos.getUserId() + "\n\t" +
                            "userName: \t\t\t\t\t" + userInfos.getUsername() + "\n\t" +
                            "passWord: \t\t\t\t\t" + userInfos.getPassword() + "\n\t" +
                            "token: \t\t\t\t\t\t" + userInfos.getToken() + "\n" +
                            "-----------------------------------------------------------------------------------------------------------------------------");
            logger.info("显示用户信息");
        }
        return RetKit.ok("userInfos", userInfos);
    }

}
