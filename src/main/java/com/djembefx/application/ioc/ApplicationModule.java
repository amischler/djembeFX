package com.djembefx.application.ioc;

import com.djembefx.application.handler.DeleteNoteHandler;
import com.djembefx.application.handler.Handler;
import com.djembefx.application.init.CssLoaderInitializer;
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
            PlayerInitializer playerInitializer,
            CssLoaderInitializer cssLoaderInitializer) {
        List<Initializer> initializerList = new LinkedList<Initializer>();
        initializerList.add(soundRendererInitializer);
        initializerList.add(playerInitializer);
        initializerList.add(cssLoaderInitializer);
        return initializerList;
    }

    @Provides
    @Singleton
    public List<Handler> providesHandler(DeleteNoteHandler deleteNoteHandler) {
        List<Handler> handlerList = new LinkedList<Handler>();
        handlerList.add(deleteNoteHandler);
        return handlerList;
    }

}
