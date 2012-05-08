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

    private StringProperty path = new SimpleStringProperty();

    private ObservableList<Loop> loops = FXCollections.observableList(new LinkedList<Loop>());

    public ObservableList<Loop> getLoops() {
        return loops;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title1) {
        this.title.set(title1);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String path1) {
        this.path.set(path1);
    }

    public StringProperty pathProperty() {
        return path;
    }

}
