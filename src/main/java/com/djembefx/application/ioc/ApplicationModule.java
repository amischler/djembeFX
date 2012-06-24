package com.djembefx.application.ioc;

import com.djembefx.application.init.Initializer;
import com.djembefx.application.init.PlayerInitializer;
import com.djembefx.application.init.SoundRendererInitializer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.LinkedList;
import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/05/12
 * Time: 20:36
 */
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    public List<Initializer> providesInitializer(
            SoundRendererInitializer soundRendererInitializer,
            PlayerInitializer playerInitializer) {
        List<Initializer> initializerList = new LinkedList<Initializer>();
        initializerList.add(soundRendererInitializer);
        initializerList.add(playerInitializer);
        return initializerList;
    }

}