package com.djembefx.controller;

import com.djembefx.model.Loop;
import com.djembefx.model.Song;
import com.djembefx.model.persistence.PersistenceService;
import com.djembefx.model.pulse.Pulser;
import com.djembefx.model.pulse.Status;
import com.djembefx.view.control.LoopPane;
import com.djembefx.view.control.LoopPaneLayout;
import com.djembefx.view.control.skin.LoopPaneSkin;
import com.dooapp.fxform.FXForm;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
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
    Pane editorStackPane;

    @FXML
    Slider zoomSlider;

    @FXML
    ToggleButton playPauseToggleButton;

    @FXML
    ChoiceBox<LoopPaneLayout> loopPaneLayoutChoiceBox;

    @Inject
    List<LoopPaneLayout> loopPaneLayoutList;

    @Inject
    LoopPane loopPane;

    @Inject
    LoopPaneSkin loopPaneSkin;

    @Inject
    PersistenceService persistenceService;

    @Inject
    Pulser pulser;

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
        loopSelectionModel.set(loopListView.getSelectionModel());
        initializeSongPropertiesPane();
        initializeLoopPropertiesPane();
        initializeLoopPane();
        initializeLoopPaneLayoutChoiceBox();
        configure(currentSong.get());
        playPauseToggleButton.textProperty().bind(
                Bindings.when(playPauseToggleButton.selectedProperty()).then("Pause").otherwise("Play"));
        playPauseToggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
                if (aBoolean1) {
                    pulser.start();
                } else {
                    pulser.pause();
                }
            }
        });
        pulser.statusProperty().addListener(new ChangeListener<Status>() {
            @Override
            public void changed(ObservableValue<? extends Status> observableValue, Status status, Status status1) {
                if (status1 == Status.RUNNING) {
                    playPauseToggleButton.setSelected(true);
                } else {
                    playPauseToggleButton.setSelected(false);
                }
            }
        });
    }

    private void initializeLoopPaneLayoutChoiceBox() {
        loopPaneLayoutChoiceBox.getItems().setAll(loopPaneLayoutList);
        loopPaneLayoutChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<LoopPaneLayout>() {
            @Override
            public void changed(ObservableValue<? extends LoopPaneLayout> observableValue, LoopPaneLayout loopPaneLayout, LoopPaneLayout loopPaneLayout1) {
                loopPane.setLoopPaneLayout(loopPaneLayout1);
            }
        });
    }

    private void initializeLoopPane() {
        loopPane.setSkin(loopPaneSkin);
        loopPane.scaleXProperty().bind(zoomSlider.valueProperty());
        loopPane.scaleYProperty().bind(zoomSlider.valueProperty());
        editorStackPane.getChildren().add(loopPane);
    }

    private void initializeLoopPropertiesPane() {
        FXForm loopPropertiesForm = new FXForm();
        loopPropertiesForm.sourceProperty().bind(loopSelectionModel.get().selectedItemProperty());
        loopPropertiesPane.getChildren().add(loopPropertiesForm);
    }

    private void initializeSongPropertiesPane() {
        FXForm songPropertiesForm = new FXForm();
        songPropertiesForm.sourceProperty().bind(currentSong);
        songPropertiesPane.getChildren().add(songPropertiesForm);
    }

    private void configure(Song song1) {
        if (song1 != null) {
            loopListView.setItems(song1.getLoops());
            loopPane.setLoops(song1.getLoops());
        }
    }

    private void unconfigure(Song song) {
        if (song != null) {
            loopListView.setItems(FXCollections.emptyObservableList());
            loopPane.setLoops(FXCollections.<Loop>emptyObservableList());
        }
    }

    @FXML
    private void addLoop(ActionEvent event) {
        Loop loop = new Loop();
        loop.setName("New loop");
        loop.setLength(32L);
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

    @FXML
    void onKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            playPause();
            keyEvent.consume();
        }
    }

    private void playPause() {
        if (pulser.getStatus() == Status.RUNNING) {
            pulser.pause();
        } else {
            pulser.start();
        }
    }

}
