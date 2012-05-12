package com.djembefx.model.event;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:22
 */
public interface EventListener<T extends Event> {

    public void onEvent(T event);

}
