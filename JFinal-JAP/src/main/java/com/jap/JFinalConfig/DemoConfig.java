package com.jap.JFinalConfig;

import com.jap.routes.AdminRoutes;
import com.jfinal.config.*;
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
    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
