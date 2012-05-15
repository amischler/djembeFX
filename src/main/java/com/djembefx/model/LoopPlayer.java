package com.djembefx.model;

import com.djembefx.model.event.EventBus;
import com.djembefx.model.event.PlayNoteEvent;
import com.google.inject.Inject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 19:42
 */
public class LoopPlayer implements Player {

    private final ObjectProperty<ObservableList<Loop>> loops = new SimpleObjectProperty<ObservableList<Loop>>(FXCollections.observableList(new LinkedList<Loop>()));

    @Inject
    EventBus eventBus;

    LoopTimer loopTimer;

    public ObservableList<Loop> getLoops() {
        return loops.get();
    }

    @Inject
    public LoopPlayer(LoopTimer loopTimer) {
        this.loopTimer = loopTimer;
        loopTimer.timeProperty().addListener(new ChangeListener<TimePosition>() {
            @Override
            public void changed(ObservableValue<? extends TimePosition> observableValue, TimePosition t1, TimePosition t2) {
                List<Note> toPlay = new LinkedList<Note>();
                for (Loop loop : loops.get()) {
                    Note note = loop.getNotes().get(new TimePosition(t1.getPosition().remainder(loop.getLength().getPosition())));
                    if (note != null) {
                        eventBus.publish(new PlayNoteEvent(note, loop.getInstrument()));
                    }
                }
            }
        });
    }

    @Override
    public void play() {
        loopTimer.setPlaying(true);
    }

    @Override
    public void pause() {
        loopTimer.setPlaying(false);
    }

    @Override
    public void stop() {
        loopTimer.stop();
    }

    public void setLoops(ObservableList<Loop> loops) {
        this.loops.set(loops);
    }

    public boolean isPlaying() {
        return loopTimer.isPlaying();
    }

    public BooleanProperty playingProperty() {
        return loopTimer.playingProperty();
    }
}
