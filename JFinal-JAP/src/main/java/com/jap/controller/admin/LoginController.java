package com.jap.controller.admin;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 登录控制器-LoginController
 */
@Clear
//测试
public class LoginController extends Controller {
    public void doLogin(){
        String username = getPara("username");
        String password = getPara("password");
    }
}
