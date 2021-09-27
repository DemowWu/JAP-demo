package com.jap.controller.admin;

import act.app.ActionContext;
import act.controller.Controller;
import act.controller.annotation.UrlContext;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.social.SocialConfig;
import com.fujieid.jap.social.SocialStrategy;
import com.jap.kit.RetKit;
import com.jap.service.JapSocialUserServiceImpl;
import com.jap.utils.HttpUtils;
import me.zhyd.oauth.config.AuthConfig;
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
 * @description ThirdLoginController
 */
@UrlContext("/social")
public class ThirdLoginController extends Controller.Util {

    private static Logger logger = LogManager.get(ThirdLoginController.class);

    @Inject
    @Singleton
    private JapSocialUserServiceImpl japSocialUserService = new JapSocialUserServiceImpl();

    private static SocialConfig config = new SocialConfig();
    private SocialStrategy socialStrategy = new SocialStrategy(japSocialUserService, new JapConfig());

    /**准备策略：<br>
     * ----SocialStrategy：选择第三方应用 social验证策略，<a href="https://justauth.plus/quickstart/jap-social.html#%E6%B5%8B%E8%AF%95%E7%99%BB%E5%BD%95">jap-social 是为了方便快速的集成第三方登录而添加的增强包</a>加入已经实现该策略验证方式的service具体类，这里是JapSocialUserServiceImpl,放进AbstractJapStrategy类(下一步认证需要使用），并初始化new config：用于设置是否是单点sso登录（默认为FALSE）、缓存和token有效时间，默认失效均时间为7天，AbstractJapStrategy中添加新的缓存JapLocalCache：单点登录——SsoJapUserStore缓存，否则SessionJapUserStore缓存，
     * 最终一并放进JapAuthentication.setContext(japUserStore,japCache,japConfig)的context。<br>
     * ----注：在准备开始authenticate时，这里的demo是对SocialConfig保持默认的配置。<br>
     * 验证授权：<br>
     * ----social策略的验证方法是SocialStrategy的authenticate实现(参数是SocialConfig的上转型、HttpServletRequest、HttpServletResponse)，首先是先进行参数SocialConfig验证配置的初始化，配置检查，检查checkSessionAndCookie中有无此时登录的用户，如果存在，则说明登录成功（非首次登录），设置状态码200并返回响应<br>
     * 如果不存在(说明是第一次登录)，就先去调用getAuthRequest拿到AuthRequest对象（授权、登录接口）—--—实现方式————>，以SocialConfig对象的上转型AuthenticateConfig为参数，进行checkAuthenticateConfig检查配置完成且其子类socialConfig.getJustAuthConfig()非空后，<br>
     * 以第三方应用Platform平台（不能为空），socialConfig, authConfig，authStateCache为参数,去拿并返回AuthRequest实例（后面去授权、登录需要调用该实例接口）<br>
     * 参数准备：<br>
     * <li>socialConfig：authenticate函数第一个参数转为子类SocialConfig对象</li>
     * <li>source：从socialConfig拿到第三方平台名称</li>
     * <li>authCallback（存有基本的授权参数）：把HttpServletRequest的所有参数转为Map集合（参数若为null，则new一个AuthCallback对象，否则遍历集合，进一步存入JSON对象中，由JSON的toJavaObject，拿到AuthCallback），</li>
     * <br>
     * 判断是否已经回调isCallback(String source, AuthCallback authCallback) ：若默认第三方应用名字与参数source相同且authCallback的token非空，则返回TRUE，否则去authCallback拿到code值（这里需要特别判断一下授权码code是否是来源于ALIPAY或者HUAWEI），若code为空，返回TRUE<br>
     * <li>isCallback返回值为FALSE：调用authRequest.authorize(socialConfig.getState())，返回JapResponse.success(url)，授权成功的响应，同意授权后URL中会带有生成的授权码code，再调用一次回调函数，进入该方法中，由code去拿到token</li>
     * <li>isCallback返回值为TRUE：调用authRequest.login(request, response, source, authRequest, authCallback)————实现方式——---->实例化AuthResponse对象，用于存储后面通过token拿拿到用户信息的响应，由响应的用户ID和第三方平台名字为参数调用japUserService的getByPlatformAndUid去模拟数据库中查询用户信息，若不存在该用户则去调用japUserService的createAndGetSocialUser，都是把查询或者创建的用户存到JapUser对象中。此时已经登录成功，但需要把用户相关信息存入JapContext的UserStore（调用其接口实现，有单点登录Store和Session的Store存储）中。</li>
     */
    @Action({"auth", "getData"})
    public RetKit renderAuth(ActionContext context){
//        获取参数，这里是为了方便测试者以页面通用的方式进行输入，而不是修改源代码。但参数clientSecret等一般很重要，实际开发下不会这样使用
        Map<String, String[]> params = context.allParams();
        String[] clientIds = params.get("clientId");
        String[] clientSecrets = params.get("clientSecret");
        String[] redirectURIS = params.get("redirectURI");

        if (clientIds != null && clientSecrets != null && redirectURIS != null){
            config.setPlatform("gitee");
            config.setState(UuidUtils.getUUID());
            config.setJustAuthConfig(AuthConfig.builder()
                    .clientId(clientIds[0])
                    .clientSecret(clientSecrets[0])
                    .redirectUri(redirectURIS[0])
                    .build());
            System.out.println("重定向地址："+redirectURIS[0]);
            logger.info("拿到参数，开始准备重定向授权页面");
        }

        logger.info("获取到code："+context.paramVal("code"));

        HttpServletRequest request = HttpUtils.toHttpServletConReq(context);
        HttpServletResponse response = HttpUtils.toHttpServletConRes(context);

        JapResponse japResponse = socialStrategy.authenticate(config, request, response);
        if (!japResponse.isSuccess()) {
            logger.info("授权失败：%s。", japResponse.getData());
            context.renderArg("响应失败。");
        }
        Map<String,String> userInfos = new HashMap<>();

        if (japResponse.isRedirectUrl()) {
            //判断japResponse的data是否有一个以http开头的URL(回调地址)：((String)data).startsWith("http")
            logger.info("授权成功");
            return RetKit.ok("toAuth",japResponse.getData());
        }
        else {
            //拿取的是JapSocialUserServiceImpl下的模拟数据库数据，通过uuid进行比较进行查找（实际开发中是通过source与uuid共同查找），是否当前数据库数据中存在此时授权用户，否则会进行创建该用户信息，并加入模拟数据库中
            logger.info("开始获取用户数据");
            JapUser japUser = (JapUser) japResponse.getData();
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------------------------\n\t"+
                            "your information:\n\t"+
                            "userId: \t\t\t\t\t"+japUser.getUserId()+ "\n\t" +
                            "userName: \t\t\t\t\t"+japUser.getUsername() + "\n\t" +
                            "passWord: \t\t\t\t\t"+japUser.getPassword() + "\n\t" +
                            "token: \t\t\t\t\t\t"+japUser.getToken() + "\n"+
                            "-----------------------------------------------------------------------------------------------------------------------------");

            userInfos.put("token",japUser.getToken());
            userInfos.put("username",japUser.getUsername());
            userInfos.put("userId",japUser.getUserId());
            userInfos.put("password",japUser.getPassword());

        }
        return   RetKit.ok("userInfos",userInfos);
    }
}
