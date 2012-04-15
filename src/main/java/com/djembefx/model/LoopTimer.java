package com.djembefx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

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

    private final Timer timer = new Timer("Beat timer", true);

    public TimePosition getTime() {
        return time.get();
    }

    public ReadOnlyObjectProperty<TimePosition> timeProperty() {
        return time;
    }


    public void play() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time.set(new TimePosition(getTime().getPosition().add(BigInteger.ONE)));
            }
        }, 0, 20);
    }

    public void pause() {
        timer.cancel();
    }

    public void stop() {
        timer.cancel();
        time.set(new TimePosition(BigInteger.ZERO));
    }
}
