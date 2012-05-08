package com.djembefx.controller;

import com.djembefx.model.Loop;
import com.djembefx.model.Song;
import com.dooapp.fxform.FXForm;
import com.google.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 11:59
 */
public class DjembeFXController extends AbstractController {

    @Inject
    ObjectProperty<Song> currentSong;

    @Inject
    ObjectProperty<SelectionModel<Loop>> loopSelectionModel;

    @FXML
    ListView loopListView;

    @FXML
    FXForm songPropertiesForm;

    @FXML
    FXForm loopPropertiesForm;

    private ListChangeListener<Loop> loopChangeListener = new ListChangeListener<Loop>() {
        @Override
        public void onChanged(Change<? extends Loop> change) {
            while (change.next()) {
                loopListView.getItems().addAll(change.getAddedSubList());
                loopListView.getItems().removeAll(change.getRemoved());
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        currentSong.addListener(new ChangeListener<Song>() {
            @Override
            public void changed(ObservableValue<? extends Song> observableValue, Song song, Song song1) {
                unconfigure(song);
                configure(song1);
            }
        });
        configure(currentSong.get());
        loopSelectionModel.set(loopListView.getSelectionModel());
        songPropertiesForm.sourceProperty().bind(currentSong);
        loopPropertiesForm.sourceProperty().bind(loopSelectionModel.get().selectedItemProperty());
    }

    private void configure(Song song1) {
        if (song1 != null) {
            song1.getLoops().addListener(loopChangeListener);
            loopListView.getItems().setAll(song1.getLoops());
        }
    }

    private void unconfigure(Song song) {
        if (song != null) {
            song.getLoops().remove(loopChangeListener);
            loopListView.getItems().clear();
        }
    }

    @FXML
    private void addLoop(ActionEvent event) {
        Loop loop = new Loop();
        loop.setName("New loop");
        currentSong.get().getLoops().add(loop);
    }

    @FXML
    private void deleteLoop(ActionEvent event) {
        currentSong.get().getLoops().remove(loopSelectionModel.get().getSelectedItem());
    }
}
