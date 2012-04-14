package com.djembefx.view;

import com.djembefx.model.Loop;
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

    private ObservableList<Loop> loops = FXCollections.observableList(new LinkedList<Loop>());

    public ObservableList<Loop> getLoops() {
        return loops;
    }
}
