package com.djembefx.application;

import com.djembefx.application.init.Initializer;
import com.djembefx.ioc.IOC;
import com.djembefx.model.Song;
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
        currentSong.set(Demo.createDemoSong());
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
