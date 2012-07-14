package com.djembefx.application.handler;

import com.djembefx.application.event.Event;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/07/12
 * Time: 23:27
 */
public interface Handler<E extends Event> {

    public Class<E> getEventClass();

    public void handle(E event);

}
