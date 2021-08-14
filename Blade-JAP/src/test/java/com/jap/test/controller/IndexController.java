package com.jap.test.controller;

import com.blade.mvc.RouteContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PostRoute;
import com.blade.mvc.ui.RestResponse;

/**
 * @author hq.W
 * @program JAP-demo
 * @description IndexController
 */
@Path
public class IndexController {
    @GetRoute("signin")
    public String signin(){
        return "templates/signin.html";
    }

    @PostRoute("signin")
    @JSON
    public RestResponse doSignin(RouteContext ctx){
        // do something
        return RestResponse.ok();
    }
}
