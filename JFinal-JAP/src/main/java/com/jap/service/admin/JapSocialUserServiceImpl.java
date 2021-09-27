package com.jap.service.admin;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;

import java.util.List;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 第三方登录JapSocialUserServiceImpl
 * @create 2021-08-12 00:13
 */
@Slf4j
public class JapSocialUserServiceImpl implements JapUserService {
    private static final List<JapUser> userDatas = Lists.newArrayList();

    static {
        userDatas.add(new JapUser().setUsername("test").setPassword("jap").setUserId("123456"));
    }

    @Override
    public JapUser getByPlatformAndUid(String platform, String uid) {
        // FIXME 注意：此处仅作演示用，并没有判断 platform，实际业务系统中需要根据 platform 和 uid 进行获取唯一用户
        //FIXME 可在SocialStrategy类的login查看，该方法的调用，注：在login方法中调用该方法前，AuthUser socialUser = (AuthUser)authUserAuthResponse.getData()已经能够拿到第三方应用的用户信息（token换取成功）

        return userDatas.stream().filter(japUser -> japUser.getUserId().equals(uid)).findFirst().orElse(null);
    }

    @Override
    public JapUser createAndGetSocialUser(Object userInfo) {
        AuthUser authUser = (AuthUser) userInfo;
        // 查询绑定关系，确定当前用户是否已经登录过业务系统
        JapUser japUser = this.getByPlatformAndUid(authUser.getSource(), authUser.getUuid());
        if (null == japUser) {
            japUser = createJapUser();
            japUser.setUserId(authUser.getUuid());
            japUser.setUsername(authUser.getUsername());
            japUser.setToken(authUser.getToken().getOauthToken());
            japUser.setPassword("模拟测试，未获取密码信息");
            japUser.setAdditional(authUser);
            userDatas.add(japUser);
        }
        return japUser;
    }
    /**
     * 模拟创建用户
     *
     * @return JapUser
     */
    private JapUser createJapUser() {
        JapUser user = new JapUser();
        user.setUserId("1");
        user.setUsername("jap");
        user.setPassword("jappassword");
        return user;
    }
}
