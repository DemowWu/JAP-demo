package com.jap.service.admin;

import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.google.common.collect.Lists;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.UuidUtils;

import java.util.List;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 第三方登录JapSocialUserServiceImpl
 * @create 2021-08-12 00:13
 */
public class JapSocialUserServiceImpl implements JapUserService {
    private static final List<JapUser> userDatas = Lists.newArrayList();

    static {
        // 模拟数据库中的数据
        userDatas.add(new JapUser().setUsername("jap").setPassword("jap"));
//        每个Japuser的token需要由Uuid参与
        for (int i = 0; i < 10; i++) {
            userDatas.add(new JapUser().setUsername("jap" + i).setPassword("jap" + i).setUserId(UuidUtils.getUUID()));
        }
    }

    @Override
    public JapUser getByPlatformAndUid(String platform, String uid) {
        // FIXME 注意：此处仅作演示用，并没有判断 platform，实际业务系统中需要根据 platform 和 uid 进行获取唯一用户
        return userDatas.stream().filter(user -> user.getUserId().equals(uid)).findFirst().orElse(null);
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
