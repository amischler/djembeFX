package com.djembefx.model;

import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 19:42
 */
public class LoopPlayer implements Player {

    private final ObservableList<Loop> loops = FXCollections.observableList(new LinkedList<Loop>());

    LoopTimer loopTimer;

    public ObservableList<Loop> getLoops() {
        return loops;
    }

    @Inject
    public LoopPlayer(LoopTimer loopTimer) {
        this.loopTimer = loopTimer;
        loopTimer.timeProperty().addListener(new ChangeListener<TimePosition>() {
            @Override
            public void changed(ObservableValue<? extends TimePosition> observableValue, TimePosition t1, TimePosition t2) {
                System.out.println("Pulse " + t2);
                for (Loop loop : loops) {
                    Note note = loop.getNotes().get(new TimePosition(t2.getPosition().remainder(loop.getLength().getPosition())));
                    if (note != null) {
                        playNote(note);
                    }
                }
            }
        });
    }

    private void playNote(Note note) {
        System.out.println("Playing " + note);
    }

    @Override
    public void play() {
        loopTimer.play();
    }

    @Override
    public void pause() {
        loopTimer.pause();
    }

    @Override
    public void stop() {
        loopTimer.stop();
    }
}
