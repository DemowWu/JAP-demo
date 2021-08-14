package com.jap.JFinalConfig;

import com.jap.Handler.GlobalHandler;
import com.jap.controller.admin.IndexJfinalController;
import com.jap.interceptor.JapInterceptor;
import com.jap.interceptor.KuayuInterceptor;
import com.jap.Handler.MyStaticHandler;
import com.jap.routes.AdminRoutes;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.FakeStaticHandler;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.template.Engine;

/**
 * @author hq.W
 * @program JAP-demo
 * @description Jfinal配置类-DemoConfig
 */
public class DemoConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants constants) {
        constants.setDevMode(true);
        constants.setInjectDependency(true);
        constants.setInjectSuperClass(true);
        constants.setJsonFactory(new FastJsonFactory());
        constants.setError404View("templates/404.html");
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add(new AdminRoutes());
    }

    @Override
    public void configEngine(Engine engine) {
    }

    @Override
    public void configPlugin(Plugins plugins) {

    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new KuayuInterceptor());
        interceptors.add(new JapInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {
        handlers.add(new FakeStaticHandler(".html"));//默认HTML
        handlers.add(new ContextPathHandler("ctx"));
        handlers.add(new GlobalHandler());
        handlers.add(new MyStaticHandler());

    }
}
