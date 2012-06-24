package com.djembefx.application;

import com.djembefx.application.init.Initializer;
import com.djembefx.ioc.IOC;
import com.djembefx.model.*;
import com.djembefx.model.persistence.PersistenceService;
import com.google.inject.Inject;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

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
    List<Initializer> initializerList;

    private StringProperty stageTitle;

    @java.lang.Override
    public void start(Stage stage) throws Exception {
        initIoc();
        initInitializers();
        this.stageTitle = stage.titleProperty();
        configure(currentSong.get());
        currentSong.addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observableValue, Song song, Song song1) {
                unconfigure(song);
                configure(song1);
            }
        });
        showStage(stage);
        Song song = new Song();
        Loop loop = new Loop();
        loop.setLength(128);
        loop.setInstrument(new Djembe());
        loop.getNotes().put(0L, new Note(DjembeType.SLAP));
        loop.getNotes().put(48L, new Note(DjembeType.SLAP));
        loop.getNotes().put(64L, new Note(DjembeType.SLAP));
        loop.getNotes().put(96L, new Note(DjembeType.TONE));
        loop.getNotes().put(112L, new Note(DjembeType.TONE));
        Loop loop1 = new Loop();
        loop1.setLength(128);
        loop1.setInstrument(new Djembe());
        loop1.getNotes().put(0L, new Note(DjembeType.OPEN));
        loop1.getNotes().put(32L, new Note(DjembeType.TONE));
        loop1.getNotes().put(48L, new Note(DjembeType.TONE));
        loop1.getNotes().put(96L, new Note(DjembeType.SLAP));
        song.getLoops().addAll(loop, loop1);
        currentSong.set(song);
        //currentSong.set(persistenceService.load(DjembeFX.class.getClassLoader().getResource("com/djembefx/demo/demo.xml").toURI().getPath()));
    }

    private void showStage(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

    private void initInitializers() {
        for (Initializer initializer : initializerList) {
            initializer.init();
        }
    }

    private void initIoc() {
        IOC.getInjector().injectMembers(this);
    }

    private void configure(Song song1) {
        if (song1 == null)
            return;
        stageTitle.bind(new SimpleStringProperty("DjembeFX - ").concat(song1.titleProperty()));
    }

    private void unconfigure(Song song) {
        stageTitle.unbind();
        stageTitle.set("DjembeFX");
    }

    public static void main(String... args) {
        Application.launch(args);
    }

}
