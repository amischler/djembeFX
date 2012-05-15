package com.djembefx.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:43
 */
public class LoopTimer {

    private final ObjectProperty<TimePosition> time = new SimpleObjectProperty<TimePosition>(new TimePosition(BigInteger.ZERO));

    private Timer timer;

    private BooleanProperty playing = new SimpleBooleanProperty();

    public LoopTimer() {
        playing.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean1) {
                if (aBoolean1) {
                    play();
                } else {
                    pause();
                }
            }
        });
    }

    public TimePosition getTime() {
        return time.get();
    }

    public ReadOnlyObjectProperty<TimePosition> timeProperty() {
        return time;
    }

    private void play() {
        getTimer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time.set(new TimePosition(getTime().getPosition().add(BigInteger.ONE)));
            }
        }, 0, 20);
    }

    private void pause() {
        timer.cancel();
        timer = null;
    }

    public void stop() {
        playing.set(false);
        reset();
    }

    public void reset() {
        time.set(new TimePosition(BigInteger.ZERO));
    }

    private Timer getTimer() {
        if (timer == null) {
            timer = new Timer("Beat timer", true);
        }
        return timer;
    }

    public void setPlaying(Boolean playing1) {
        this.playing.set(playing1);
    }

    public boolean isPlaying() {
        return playing.get();
    }

    public BooleanProperty playingProperty() {
        return playing;
    }
}
