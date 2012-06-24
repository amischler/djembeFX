package com.djembefx.model.ioc;

import com.djembefx.model.event.ioc.EventModule;
import com.djembefx.model.persistence.ioc.PersistenceModule;
import com.djembefx.model.pulse.Pulser;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 21:23
 */
public class ModelModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new PersistenceModule());
        install(new EventModule());
        bind(Pulser.class).in(Singleton.class);
    }

}
