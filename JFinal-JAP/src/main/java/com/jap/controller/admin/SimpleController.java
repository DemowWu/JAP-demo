package com.jap.controller.admin;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.simple.SimpleConfig;
import com.fujieid.jap.simple.SimpleStrategy;
import com.jap.kit.RetKit;
import com.jap.service.admin.JapSimpleUserServiceImpl;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 账号密码登录-SimpleController
 */
@Slf4j
public class SimpleController extends Controller {

    private static Logger logger = LoggerFactory.getLogger(SimpleController.class);
    @Inject(JapSimpleUserServiceImpl.class)
    private JapUserService japUserService;

    /**准备策略：<br>
    * ----SimpleStrategy：选择simple验证策略，加入已经实现该策略验证方式的service具体类，这里是JapSimpleUserServiceImpl,放进AbstractJapStrategy类(下一步认证需要使用），并初始化new config：用于设置是否是单点sso登录（默认为FALSE）、缓存和token有效时间，默认失效均时间为7天，AbstractJapStrategy中添加新的缓存JapLocalCache：单点登录——SsoJapUserStore缓存，否则SessionJapUserStore缓存，
    * 最终一并放进JapAuthentication.setContext(japUserStore,japCache,japConfig)的context。<br>
    * ----注：在准备开始authenticate时，这里的demo是对simpleConfig保持默认的配置。<br>
    * 验证授权：<br>
    * ----simple策略的验证方法是SimpleStrategy的authenticate实现，首先是参数SimpleConfig验证配置的初始化，经过一系列配置检查后，检查checkSessionAndCookie中有无此时登录的用户，如果存在，则说明登录成功，设置状态码200并返回响应<br>
    * 如果不存在(说明是第一次登录)，就先去doResolveCredential：判断username和password是否同时非空，满足则封装为一个Credential并初始化该类变量，其中的rememberMe从request的getParam("rememberMe")函数进行判断，1——>Credential.getrememberMe()为真，先使用使用service具体类的getByName方法，未查到该用户账号，响应用户不存在NOT_EXIST_USER，否则继续使用validPassword，判断密码是否有效,
    * 密码也正确，此时两个方向判断：先判断有没有实现rememberMe ——有——>测需要先addCookie，再跳转loginSuccess，设置状态码200，否则，直接跳转loginSuccess。
    */

    @ActionKey("/logined")
    public void renderAuth() {
        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");

        logger.info("*********************************************");
        logger.info("username："+ username+"     password："+password);
        logger.info("*********************************************");

        SimpleStrategy simpleStrategy = new SimpleStrategy(japUserService, new JapConfig());
        JapResponse japResponse = simpleStrategy.authenticate(new SimpleConfig(), this.getRequest(), this.getResponse());

        if (!japResponse.isSuccess()) {
            renderJson(RetKit.fail("验证失败"));
        }
        if (japResponse.isRedirectUrl()) {
            //判断japResponse的data是否有一个以http开头的URL(回调地址)：((String)data).startsWith("http")
            renderText("isRedirectUrl——————>"+(String) japResponse.getData());
        }
        else {
            JapUser data = (JapUser) japResponse.getData();
            JapUser userInfos = new JapUser();
            userInfos.setUserId(data.getUserId());
            userInfos.setUsername(data.getUsername());
            userInfos.setPassword(data.getPassword());
            userInfos.setToken(data.getToken());
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------\n\t"+
                            "your information:\n\t"+
                            "userId: \t\t\t\t\t"+userInfos.getUserId()+ "\n\t" +
                            "userName: \t\t\t\t\t"+userInfos.getUsername() + "\n\t" +
                            "passWord: \t\t\t\t\t"+userInfos.getPassword() + "\n\t" +
                            "token: \t\t\t\t\t\t"+userInfos.getToken() + "\n"+
                            "-----------------------------------------------------------------------------------------------------------------------------");

            renderJson(RetKit.ok("userInfos",userInfos));
        }
    }
}
