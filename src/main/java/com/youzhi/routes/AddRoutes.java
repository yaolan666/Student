package com.youzhi.routes;

import com.jfinal.config.Routes;
import com.youzhi.controller.IntercepterController;
import com.youzhi.interceptor.DemoInterceptor;

public class AddRoutes extends Routes {

    @Override
    public void config() {
        addInterceptor(new DemoInterceptor());
        add("/id", IntercepterController.class);
    }
}
