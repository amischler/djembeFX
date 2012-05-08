package com.djembefx.controller.ioc;

import com.djembefx.model.Loop;
import com.djembefx.model.Song;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.SelectionModel;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 12:20
 */
public class ControllerModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    ObjectProperty<Song> providesCurrentSong() {
        return new SimpleObjectProperty<Song>(new Song());
    }

    @Provides
    @Singleton
    ObjectProperty<SelectionModel<Loop>> providesLoopSelectionModel() {
        return new SimpleObjectProperty<SelectionModel<Loop>>();
    }
}
