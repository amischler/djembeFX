package com.djembefx.application.init;

import com.djembefx.model.Loop;
import com.djembefx.model.LoopPlayer;
import com.djembefx.model.Song;
import com.google.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/05/12
 * Time: 20:27
 */
public class PlayerInitializer implements Initializer {

    @Inject
    ObjectProperty<Song> currentSong;

    @Inject
    LoopPlayer loopPlayer;

    @Override
    public void init() {
        configure(currentSong.get());
        currentSong.addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observableValue, Song song, Song song1) {
                unconfigure(song);
                configure(song1);
            }
        });
    }

    private void configure(Song song1) {
        loopPlayer.setLoops(song1.getLoops());
    }

    private void unconfigure(Song song) {
        loopPlayer.setLoops(FXCollections.<Loop>emptyObservableList());
    }

}
