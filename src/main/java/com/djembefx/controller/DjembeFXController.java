package com.djembefx.controller;

import com.djembefx.model.Loop;
import com.djembefx.model.Song;
import com.djembefx.model.persistence.PersistenceService;
import com.djembefx.view.control.LoopPane;
import com.djembefx.view.control.skin.LoopPaneSkin;
import com.dooapp.fxform.FXForm;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 11:59
 */
public class DjembeFXController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(DjembeFXController.class);

    @Inject
    ObjectProperty<Song> currentSong;

    @Inject
    ObjectProperty<SelectionModel<Loop>> loopSelectionModel;

    @FXML
    ListView loopListView;

    @FXML
    AnchorPane songPropertiesPane;

    @FXML
    AnchorPane loopPropertiesPane;

    @FXML
    ScrollPane editorScrollPane;

    @Inject
    LoopPane loopPane;

    @Inject
    LoopPaneSkin loopPaneSkin;

    @Inject
    PersistenceService persistenceService;

    private ListChangeListener<Loop> loopChangeListener = new ListChangeListener<Loop>() {
        @Override
        public void onChanged(Change<? extends Loop> change) {
            while (change.next()) {
                loopListView.getItems().addAll(change.getAddedSubList());
                loopListView.getItems().removeAll(change.getRemoved());
                loopPane.getLoops().addAll(change.getAddedSubList());
                loopPane.getLoops().removeAll(change.getRemoved());
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
        FXForm songPropertiesForm = new FXForm();
        songPropertiesForm.sourceProperty().bind(currentSong);
        songPropertiesPane.getChildren().add(songPropertiesForm);
        FXForm loopPropertiesForm = new FXForm();
        loopPropertiesForm.sourceProperty().bind(loopSelectionModel.get().selectedItemProperty());
        loopPropertiesPane.getChildren().add(loopPropertiesForm);
        loopPane.setSkin(loopPaneSkin);
        editorScrollPane.setContent(loopPane);
    }

    private void configure(Song song1) {
        if (song1 != null) {
            song1.getLoops().addListener(loopChangeListener);
            loopListView.getItems().setAll(song1.getLoops());
            loopPane.getLoops().setAll(song1.getLoops());
        }
    }

    private void unconfigure(Song song) {
        if (song != null) {
            song.getLoops().remove(loopChangeListener);
            loopListView.getItems().clear();
            loopPane.getLoops().clear();
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

    @FXML
    void quit() {
        Platform.exit();
    }

    @FXML
    void newFile() {
        Song song = new Song();
        song.setTitle("New song");
        currentSong.set(song);
    }

    @FXML
    void saveFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            persistenceService.save(currentSong.get(), file.getAbsolutePath());
        }
    }

    @FXML
    void openFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                currentSong.set(persistenceService.load(file.getAbsolutePath()));
            } catch (FileNotFoundException e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    @FXML
    void closeFile() {
        currentSong.set(null);
    }

}
