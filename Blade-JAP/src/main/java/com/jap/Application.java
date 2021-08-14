package com.jap;

import com.blade.Blade;
import com.blade.mvc.WebContext;
import com.blade.mvc.annotation.Param;
import com.blade.mvc.annotation.PostRoute;

/**
 * @author hq.W
 * @program JAP-demo
 * @description main
 * @create 2021-07-20 15:30
 */
public class Application {
    public static void main(String[] args){
        Blade.of()
//                .use()早于webhook执行 中间件
                .start(Application.class,args);
    }
}
