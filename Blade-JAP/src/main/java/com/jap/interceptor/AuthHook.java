package com.jap.interceptor;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;
import com.blade.mvc.http.Body;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 1-AuthHook
 */
@Bean
@Slf4j
public class AuthHook implements WebHook {
    @Override
    public boolean before(RouteContext routeContext) {
        log.info("开始拦截");
        Body body = routeContext.body();
        System.out.println(body.toString());
        log.info("request URL: {} {}",routeContext.address(),routeContext.route());
        return true;
    }
}
