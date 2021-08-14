package com.jap.test;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 首页-IndexJfinalController
 */
public class IndexJfinalController extends Controller {
    @ActionKey("/JFinal-index")
//    test
    public void index(){
        render("index.html");
    }
}
