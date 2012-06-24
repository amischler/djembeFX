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

    private final StringProperty name = new SimpleStringProperty();

    private final LongProperty length = new SimpleLongProperty();

    private final ObservableMap<Long, Note> notes = FXCollections.observableMap(new HashMap<Long, Note>());

    private final ObjectProperty<Instrument> instrument = new SimpleObjectProperty<Instrument>();

    public long getLength() {
        return length.get();
    }

    public void setLength(long length) {
        this.length.set(length);
    }

    public LongProperty lengthProperty() {
        return length;
    }

    public ObservableMap<Long, Note> getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    @Override
    public String toString() {
        return getName();
    }

    public Instrument getInstrument() {
        return instrument.get();
    }

    public void setInstrument(Instrument instrument1) {
        this.instrument.set(instrument1);
    }

    public ObjectProperty<Instrument> instrumentProperty() {
        return instrument;
    }

}
