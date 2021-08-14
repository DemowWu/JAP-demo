package com.jap.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

/**
 * @author hq.W
 * @program JAP-demo
 * @description IndexControll
 * @create 2021-08-12 10:28
 */
public class IndexControll extends Controller {
//    @ActionKey("")
    public void index(){
        render("/templates/index.html");
    }
}
