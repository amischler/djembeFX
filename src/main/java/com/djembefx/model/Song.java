package com.djembefx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 12:19
 */
public class Song {

    private StringProperty title = new SimpleStringProperty();

    private ObservableList<Loop> loops = FXCollections.observableList(new LinkedList<Loop>());

    public ObservableList<Loop> getLoops() {
        return loops;
    }
}
