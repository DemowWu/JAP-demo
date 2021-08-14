package com.jap.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description Vue跨域问题-KuayuInterceptor
 */
public class KuayuInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        System.out.println("跨域拦截处理开始");
        if (invocation.isActionInvocation()){
//            获取Action的对应controller
            Controller controller = invocation.getController();
            HttpServletResponse response = controller.getResponse();
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8082");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Credentials", "true");

            response.setHeader("Access-Control-Expose-Headers", "*");
            response.setHeader("Access-Control-Allow-Headers",
                    "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        }
        invocation.invoke();
        System.out.println("跨域拦截处理结束");
    }
}
