package com.jap.Handler;

import com.jfinal.handler.Handler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 静态页面加载 handler-MyStaticHandler
 */
public class MyStaticHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.contains("/static/")) {
            String path = "static/" + StringUtils.substringAfter(target, "/static/");
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
            OutputStream outputStream = null;
            try {
                if (inputStream != null) {
                    outputStream = response.getOutputStream();
                    IOUtils.copy(inputStream, response.getOutputStream());
                }
            } catch (IOException e) {
                System.out.println("cant get static resource : " + path + " from jar————>"+e);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
            isHandled[0] = true;
        } else {
            this.next.handle(target, request, response, isHandled);
        }
    }
}
