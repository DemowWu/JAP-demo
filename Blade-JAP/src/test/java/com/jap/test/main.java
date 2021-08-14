package com.jap.test;

import com.blade.Blade;
import com.jap.Application;

/**
 * @author hq.W
 * @program JAP-demo
 * @description main
 */
public class main {
    public static void main(String[] args){
        Blade.me()
                .get("/",((request, response) -> response.text("hello Blade!!")))
                .get("/hello",(request, response) ->{
                    response.html("<h1>Hello World</h1>");
                }).start();

//        WebContext.blade() 获取上下文对象
    }
}
