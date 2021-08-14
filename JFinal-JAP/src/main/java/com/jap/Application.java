package com.jap;

import com.jap.JFinalConfig.DemoConfig;
import com.jfinal.server.undertow.UndertowServer;

/**
 * @author hq.W
 * @program JAP-demo
 * @description 启动类-main
 * @create 2021-07-20 15:25
 */
public class Application {
    public static void main(String[] args) {
        //以文件方式 设置JFinal、端口号，并开启DevMode
        UndertowServer.create(DemoConfig.class).start();
    }
}
