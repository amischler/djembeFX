package com.djembefx.view.ioc;

import com.djembefx.view.control.LoopPane;
import com.djembefx.view.MainParent;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:29
 */
public class ViewModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LoopPane.class).in(Singleton.class);
        bind(Parent.class).annotatedWith(Names.named("scene")).to(MainParent.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Scene provideScene(@Named("scene") Parent parent) {
        Scene scene = new Scene(parent);
        return scene;
    }

}
