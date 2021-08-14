package com.jap.service;

import act.inject.AutoBind;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.JapUserService;
import com.google.common.collect.Lists;
import me.zhyd.oauth.utils.UuidUtils;
import javax.inject.Named;
import java.util.List;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 账号密码登录-JapSimpleUserServiceImpl
 */
@AutoBind
@Named("simpleImpl")
public class JapSimpleUserServiceImpl implements JapUserService {
    /**
     * 模拟 DB 操作
     */
    private static final List<JapUser> userDatas = Lists.newArrayList();

    static {
        // 模拟数据库中的数据
        userDatas.add(new JapUser().setUsername("jap").setPassword("jap"));
//        每个Japuser的token需要由Uuid参与
        for (int i = 0; i < 10; i++) {
            userDatas.add(new JapUser().setUsername("jap" + i).setPassword("jap" + i).setUserId(UuidUtils.getUUID()));
        }
    }
    /**
    * simple：账号密码登录，则需要实现getByName和validPassword两个方法，以便authenticate认证账号密码有效性
    */
    @Override
    public JapUser getByName(String username) {
        return userDatas.stream()
                .filter((user) -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean validPassword(String password, JapUser user) {
        return user.getPassword().equals(password);
    }
}
