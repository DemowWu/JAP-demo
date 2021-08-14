package com.jap.controller.admin;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.JsonKit;
import com.blade.kit.json.Ason;
import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.http.HttpResponse;
import com.blade.mvc.http.Request;
import com.fujieid.jap.core.JapUser;
import com.fujieid.jap.core.config.JapConfig;
import com.fujieid.jap.core.result.JapResponse;
import com.fujieid.jap.simple.SimpleConfig;
import com.fujieid.jap.simple.SimpleStrategy;
import com.jap.service.admin.JapSimpleUserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author hq.W
 * @program JAP-demo
 * @description SimpleController
 */
@Path("simple")
@Slf4j
public class SimpleController{

    @Inject
    private JapSimpleUserServiceImpl japSimpleUserService;
    @GetRoute("index")
    public void index(RouteContext ctx){
        ctx.response().render("simplePage/loginpage.html");
    }
    @GetRoute("test")
    public void test(HttpResponse response){
        response.text("测试");
    }
    @PostRoute("toLogin")
    @JSON
    public void get(RouteContext rtx){
        String username = rtx.fromString("username");
        String password = rtx.fromString("password");

        log.info("username = "+username);
        log.info("password = "+password);

        rtx.request();

        SimpleStrategy simpleStrategy = new SimpleStrategy(japSimpleUserService,new JapConfig());
//        需要HttpServletRequest  Blade内置的自定义request
//        JapResponse japResponse = simpleStrategy.authenticate(new SimpleConfig() );
//        if (!japResponse.isSuccess()){
//            log.info("simple认证失败，自动转向自定义404错误页面");
//        }
//        if (japResponse.isRedirectUrl()){
//            System.out.println("isRedirectUrl");
//        }
//        else{
//            JapUser data = (JapUser) japResponse.getData();
//            Ason<?, ?> ason = JsonKit.toAson(String.valueOf(data));
//            System.out.println(ason);
//        }
    }
}
