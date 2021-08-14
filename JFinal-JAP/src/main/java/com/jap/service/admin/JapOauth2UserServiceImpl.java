package com.jap.service.admin;

import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.fujieid.jap.oauth2.token.AccessToken;
import com.google.common.collect.Lists;
import com.jfinal.aop.Inject;
import com.xkcoding.json.JsonUtil;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.UuidUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 实现JAP 调用（操作）开发者业务系统中用户的接口-JapOauth2UserServiceImpl
 * @create 2021-07-19 15:50
 */
public class JapOauth2UserServiceImpl implements JapUserService {
    /**
     * 模拟 DB 操作
     */
    private static final List<JapUser> userDatas = Lists.newArrayList();

    static {
        JapUser japUser = new JapUser();
        japUser.setUserId("1");
        japUser.setUsername("justauth");
        japUser.setPassword("justauthpassword");
        userDatas.add(japUser);
    }
    /**
     * 根据第三方平台标识（platform）和第三方平台的用户 uid 查询数据库
     *
     * @param platform 第三方平台标识
     * @param uid      第三方平台的用户 uid
     * @return JapUser
     */
    @Override
    public JapUser  getByPlatformAndUid(String platform, String uid) {
        // FIXME 注意：此处仅作演示用，并没有判断 platform，实际业务系统中需要根据 platform 和 uid 进行获取唯一用户，这里以gitee为例
        if(platform.equals("gitee")) {
            return userDatas.stream()
            .filter(user -> user.getUserId().equals(uid))
            .findFirst()
            .orElse(null);
        }
        return null;
    }

    /**
     * 创建并获取第三方用户，相当于第三方登录成功后，将授权关系保存到数据库（开发者业务系统中 oauth2 user -> sys user 的绑定关系）
     *
     * @param platform 第三方平台标识
     * @param userInfo JustAuth 中的 AuthUser
     * @return JapUser
     */
    @Override
    public JapUser createAndGetOauth2User(String platform, Map<String, Object> userInfo, Object tokenInfo) {
        // FIXME 业务端可以对 tokenInfo 进行保存或其他操作
        AccessToken accessToken = (AccessToken) tokenInfo;
        System.out.println("user token：——————————————》"+accessToken.getAccessToken());

        // FIXME 注意：此处仅作演示用，不同的 oauth 平台用户id都不一样，此处需要开发者自己分析第三方平台的用户信息，提取出用户的唯一ID
        String uid = (String) userInfo.get("id");
        System.out.println(uid+"*******"+platform);
        if (uid == null){
            uid = UuidUtils.getUUID();
        }
        // 查询绑定关系，确定当前用户是否已经登录过业务系统
        JapUser japUser = this.getByPlatformAndUid(platform, uid);
        if (null == japUser) {
            japUser = createJapUser();
            japUser.setAdditional(userInfo);
            userDatas.add(japUser);
        }
        return japUser;
    }
    private JapUser createJapUser() {
        JapUser user = new JapUser();
        user.setUserId("2");
        user.setUsername("justauth");
        user.setPassword("justauthpassword");
        return user;
    }

    @Override
    public JapUser createAndGetSocialUser(Object userInfo) {
        AuthUser authUser = (AuthUser) userInfo;
        // 查询绑定关系，确定当前用户是否已经登录过业务系统
        JapUser japUser = this.getByPlatformAndUid(authUser.getSource(), authUser.getUuid());
        if (null == japUser) {
            japUser = createJapUser();
            japUser.setAdditional(authUser);
            userDatas.add(japUser);
        }
        return japUser;
    }
}
