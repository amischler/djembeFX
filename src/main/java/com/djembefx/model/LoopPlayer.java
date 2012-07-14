package com.djembefx.model;

import com.djembefx.application.event.EventBus;
import com.djembefx.application.event.PlayNoteEvent;
import com.djembefx.application.pulse.PulseListener;
import com.djembefx.application.pulse.Pulser;
import com.google.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 19:42
 */
public class LoopPlayer {

    private final ObjectProperty<ObservableList<Loop>> loops = new SimpleObjectProperty<ObservableList<Loop>>(FXCollections.observableList(new LinkedList<Loop>()));

    @Inject
    EventBus eventBus;

    @Inject
    public LoopPlayer(Pulser pulser) {
        pulser.addPulseListener(new PulseListener() {
            @Override
            public void pulse(long pulse) {
                for (Loop loop : loops.get()) {
                    Note note = loop.getNotes().get(pulse % loop.getLength());
                    if (note != null) {
                        eventBus.publish(new PlayNoteEvent(note, loop.getInstrument()));
                    }
                }
            }
        });
    }

    public ObservableList<Loop> getLoops() {
        return loops.get();
    }

    public void setLoops(ObservableList<Loop> loops) {
        this.loops.set(loops);
    }

}
