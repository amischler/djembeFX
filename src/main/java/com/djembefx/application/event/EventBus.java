package com.djembefx.application.event;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:21
 */
public interface EventBus {

    public void publish(Event event);

    public void publishLater(Event event);

    public <T extends Event> void addEventListener(Class<T> clazz, EventListener<T> eventListener);

    public <T extends Event> void removeEventListener(Class<T> clazz, EventListener<T> eventListener);

}
