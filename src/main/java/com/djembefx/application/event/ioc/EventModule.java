package com.djembefx.application.event.ioc;

import com.djembefx.application.event.EventBus;
import com.djembefx.application.event.EventBusImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:49
 */
public class EventModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventBus.class).to(EventBusImpl.class).in(Singleton.class);
    }
}
