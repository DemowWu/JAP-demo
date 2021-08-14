package com.jap.controller;

import act.app.conf.AutoConfig;
import act.controller.Controller;
import org.osgl.$;
import org.osgl.logging.LogManager;
import org.osgl.logging.Logger;
import org.osgl.mvc.annotation.GetAction;
import org.osgl.mvc.annotation.PostAction;
import org.osgl.mvc.annotation.PutAction;
import org.osgl.util.Const;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 1-Test
 * @create 2021-08-13 17:41
 */

@AutoConfig
@Controller("test")
//Controller (extends BashController) 的path继承BashController，加/ 可取消BashController的path
public class Test {
    private static Logger logger = LogManager.get(Test.class);
    public static String REDIRECT_URL;
    public static final Const<String> VERB = $.constant();

    @GetAction("redirect")
    public void redirect(){
        String verb = VERB.get();
        System.out.println(verb);
        Controller.Util.redirect(REDIRECT_URL);
    }
    //    / ————> absolute path
    @GetAction("/route_a")
    public String routA(){
        return "routA";
    }
    @PostAction("route_b")
    public String postB(){
        return "routB";
    }
    @PutAction("route_c")
    public String putC(){
        return "routC";
    }

}
