package com.djembefx.model.ioc;

import com.djembefx.model.LoopTimer;
import com.djembefx.model.TimePosition;
import com.djembefx.model.event.ioc.EventModule;
import com.djembefx.model.persistence.ioc.PersistenceModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import javafx.beans.property.ReadOnlyObjectProperty;

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
        bind(LoopTimer.class).in(Singleton.class);
    }

    @Provides
    @Named("current")
    public ReadOnlyObjectProperty<TimePosition> provideCurrentTimePosition(LoopTimer timer) {
        return timer.timeProperty();
    }

}
