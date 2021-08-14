package com.jap.interceptor;

import com.fujieid.jap.core.context.JapAuthentication;
import com.fujieid.jap.core.result.JapResponse;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description JapInterceptor
 */
public class JapInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        HttpServletRequest request = controller.getRequest();
        HttpServletResponse response = controller.getResponse();

        System.out.println("请求地址："+request.getRequestURI());
        JapResponse japResponse = JapAuthentication.checkUser(request, response);
        if (japResponse.isSuccess()){
            System.out.println("登录成功");
            invocation.invoke();
        }
        System.out.println("未登录");
        controller.redirect("/oidc");
    }
}
