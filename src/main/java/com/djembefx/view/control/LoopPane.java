package com.djembefx.view.control;

import com.djembefx.model.Loop;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

import java.util.LinkedList;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 21:11
 */
public class LoopPane extends Control {

    private ObjectProperty<ObservableList<Loop>> loops = new SimpleObjectProperty<ObservableList<Loop>>(FXCollections.observableList(new LinkedList<Loop>()));

    public ObservableList<Loop> getLoops() {
        return loops.get();
    }

    public void setLoops(ObservableList<Loop> loops) {
        this.loops.set(loops);
    }

    public ObjectProperty<ObservableList<Loop>> loopsProperty() {
        return loops;
    }

}
