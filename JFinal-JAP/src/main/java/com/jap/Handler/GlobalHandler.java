package com.jap.Handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author hq.W
 * @program JAP-demo
 * @description options处理 GlobalHandler
 */
public class GlobalHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (request.getMethod().equals("OPTIONS")){
            //options 直接放行
            System.out.println("option 放行");
            response.setStatus(SC_OK);
            return;
        }
        request.setAttribute("path", request.getContextPath());
        if (target.endsWith(".html")) {
            target = target.substring(0, target.length() - 5);
        }
        next.handle(target, request, response, isHandled);
    }
}
