package com.djembefx.application;

import com.djembefx.application.init.PlayerInitializer;
import com.djembefx.application.init.SoundRendererInitializer;
import com.djembefx.ioc.IOC;
import com.djembefx.model.*;
import com.djembefx.model.persistence.PersistenceService;
import com.google.inject.Inject;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:13
 */
public class DjembeFX extends Application {

    @Inject
    ObjectProperty<Song> currentSong;

    @Inject
    Scene scene;

    @Inject
    PersistenceService persistenceService;

    @Inject
    SoundRendererInitializer soundRendererInitializer;

    @Inject
    PlayerInitializer playerInitializer;

    private Stage stage;

    @Inject
    LoopPlayer player;

    @java.lang.Override
    public void start(Stage stage1) throws Exception {
        IOC.getInjector().injectMembers(this);
        this.stage = stage1;
        stage.setScene(scene);
        stage.show();
        configure(currentSong.get());
        currentSong.addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observableValue, Song song, Song song1) {
                unconfigure(song);
                configure(song1);
            }
        });
        currentSong.set(persistenceService.load(DjembeFX.class.getClassLoader().getResource("com/djembefx/demo/demo.xml").toURI().getPath()));
        soundRendererInitializer.init();
        playerInitializer.init();
    }

    private void configure(Song song1) {
        if (song1 == null)
            return;
        stage.titleProperty().bind(new SimpleStringProperty("DjembeFX - ").concat(song1.titleProperty()));
    }

    private void unconfigure(Song song) {
        stage.titleProperty().unbind();
        stage.titleProperty().set("DjembeFX");
    }

    public static void main(String... args) {
        Application.launch(args);
    }

}
