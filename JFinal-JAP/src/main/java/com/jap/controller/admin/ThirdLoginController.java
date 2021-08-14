package com.jap.controller.admin;

import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSONObject;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.social.SocialConfig;
import com.fujieid.jap.social.SocialStrategy;
import com.jap.service.admin.JapSimpleUserServiceImpl;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.UuidUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 1-ThirdLoginCOntroller
 */
public class ThirdLoginController extends Controller {
    @Resource(name = "social")
    @Inject(JapSimpleUserServiceImpl.class)
    private JapUserService japUserService;

    @ActionKey("/social")
    public void index() {
        render("/templates/thirdLoginPage/loginpage.html");
    }

    @ActionKey("/social/auth")
    public void renderAuth() {

        SocialStrategy socialStrategy = new SocialStrategy(japUserService, new JapConfig());
        SocialConfig config = new SocialConfig();
        // platform 参考 justauth#AuthDefaultSource
        // 如果包含通过 justauth 自定义的第三方平台，则该值为实现 AuthSource 后的 getName() 值
        config.setPlatform("gitee");
        config.setState(UuidUtils.getUUID());
        config.setJustAuthConfig(AuthConfig.builder()
                .clientId("12e2f9e10829d3da70739e4e8b83c747b4ac6d78e08693bbce990ba9c2063e2a")
                .clientSecret("45a0c6b80c87358eb4bab3fe46758e5a3aebd00d8a48def4c803cf12f45b388d")
                .redirectUri("http://127.0.0.1:8091/index")
                .build());
        JapResponse japResponse = socialStrategy.authenticate(config, this.getRequest(), this.getResponse());
        if (japResponse.isSuccess() && !japResponse.isRedirectUrl()) {
            JapUser japUser = (JapUser) japResponse.getData();
            AuthUser authUser = (AuthUser) japUser.getAdditional();
            AuthToken authToken = authUser.getToken();
            try {
                JapResponse userInfoRes = socialStrategy.getUserInfo(config, authToken);
                System.out.println("通过 token 获取的用户信息：" + JSONObject.toJSONString(userInfoRes));
            } catch (Exception e) {
                System.err.println("通过 token 获取的用户信息出错：" + e.getMessage());
            }
        }
        if (!japResponse.isSuccess()) {
            renderJson("/?error=" + URLUtil.encode(japResponse.getMessage()));
        }
        if (japResponse.isRedirectUrl()) {
            redirect((String) japResponse.getData());
            System.out.println("授权成功");
        } else {
            JapUser data = (JapUser) japResponse.getData();
            renderText("登录成功\n" + "username：" + data.getUsername() + "\npassword：" + data.getPassword() + "\ntoken：" + data.getToken() + "\nuserId：" + data.getUserId());
        }
    }
}

