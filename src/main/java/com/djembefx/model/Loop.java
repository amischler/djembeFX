package com.djembefx.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:40
 */
public class Loop {

    private final ObjectProperty<TimePosition> length = new SimpleObjectProperty<TimePosition>();

    private final ObservableMap<TimePosition, Note> notes = FXCollections.observableMap(new HashMap<TimePosition, Note>());

    public TimePosition getLength() {
        return length.get();
    }

    public void setLength(TimePosition length) {
        this.length.set(length);
    }

    public ObjectProperty<TimePosition> lengthProperty() {
        return length;
    }

    public ObservableMap<TimePosition, Note> getNotes() {
        return notes;
    }

}
