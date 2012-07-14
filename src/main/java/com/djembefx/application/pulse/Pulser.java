package com.djembefx.application.pulse;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 24/06/12
 * Time: 12:50
 */
public class Pulser {

    private final LongProperty pulse = new SimpleLongProperty();

    private Timer timer;

    private long timerPulse = 0L;

    private final List<PulseListener> pulseListenerList = new LinkedList<PulseListener>();

    private final ObjectProperty<Status> status = new SimpleObjectProperty<Status>();

    public Pulser() {
        statusProperty().addListener(new ChangeListener<Status>() {
            @Override
            public void changed(ObservableValue<? extends Status> observableValue, Status status, Status status1) {
                if (status1 == Status.RUNNING) {
                    timer = new Timer("Pulse", true);
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            pulse();
                        }
                    }, 0, 10);
                } else if (status1 == Status.STOPPED) {
                    timer.cancel();
                    timerPulse = 0;
                    updatePulseInFXThread();
                } else if (status1 == Status.PAUSED) {
                    timer.cancel();
                }
            }
        });
    }

    /**
     * Start the pulser.
     */
    public void start() {
        status.set(Status.RUNNING);
    }

    private void pulse() {
        timerPulse = timerPulse + 1;
        for (PulseListener pulseListener : pulseListenerList) {
            pulseListener.pulse(timerPulse);
        }
        updatePulseInFXThread();
    }

    private void updatePulseInFXThread() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pulse.set(timerPulse);
            }
        });
    }

    /**
     * Stop the pulser.
     */
    public void stop() {
        status.set(Status.STOPPED);
    }

    /**
     * Pause the pulser.
     */
    public void pause() {
        status.set(Status.PAUSED);
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    public Status getStatus() {
        return status.get();
    }

    public void setStatus(Status status1) {
        status.set(status1);
    }

    public ReadOnlyLongProperty pulseProperty() {
        return this.pulse;
    }

    public long getPulse() {
        return this.pulse.get();
    }

    /**
     * Register a pulse listener. This listener will *not* be notified in the FX thread. If you
     * need to track the pulses in the FX thread use pulseProperty() instead.
     *
     * @param pulseListener
     */
    public void addPulseListener(PulseListener pulseListener) {
        pulseListenerList.add(pulseListener);
    }

    /**
     * Remove a pulse listener.
     *
     * @param pulseListener
     */
    public void removePulseListener(PulseListener pulseListener) {
        pulseListenerList.remove(pulseListener);
    }

}
