package com.djembefx.ioc;

import com.djembefx.controller.ioc.ControllerModule;
import com.djembefx.model.ioc.ModelModule;
import com.djembefx.view.ioc.ViewModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 11:56
 */
public class IOC {

    private static Injector injector;

    public static Injector getInjector() {
        if (injector == null) {
            init();
        }
        return injector;
    }

    private static void init() {
        injector = Guice.createInjector(new ViewModule(), new ModelModule(), new ControllerModule());
    }
}
