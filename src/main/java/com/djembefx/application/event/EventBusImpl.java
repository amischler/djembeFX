package com.djembefx.application.event;

import javafx.application.Platform;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:23
 */
public class EventBusImpl implements EventBus {

    private Map<Class, List<EventListener>> listeners = new HashMap<Class, List<EventListener>>();

    @Override
    public void publish(Event event) {
        for (EventListener eventListener : listeners.get(event.getClass())) {
            eventListener.onEvent(event);
        }
    }

    @Override
    public void publishLater(final Event event) {
        for (EventListener eventListener : listeners.get(event.getClass())) {
            final EventListener eventListener1 = eventListener;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    eventListener1.onEvent(event);
                }
            });
        }
    }

    @Override
    public <T extends Event> void addEventListener(Class<T> clazz, EventListener<T> eventListener) {
        if (!listeners.containsKey(clazz)) {
            listeners.put(clazz, new LinkedList());
        }
        listeners.get(clazz).add(eventListener);
    }

    @Override
    public <T extends Event> void removeEventListener(Class<T> clazz, EventListener<T> eventListener) {
        listeners.get(clazz).remove(eventListener);
    }


}
