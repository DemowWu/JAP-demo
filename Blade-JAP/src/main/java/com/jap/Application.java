package com.jap;

import com.blade.Blade;
import com.blade.security.web.auth.AuthOption;

/**
 * @author hq.W
 * @program JAP-demo
 * @description main
 * @create 2021-07-20 15:30
 */
public class Application {
    public static void main(String[] args){
        Blade.of().start(Application.class,args);
    }
}
