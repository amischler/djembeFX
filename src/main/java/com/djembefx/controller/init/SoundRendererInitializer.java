package com.djembefx.controller.init;

import com.djembefx.model.event.EventBus;
import com.djembefx.model.event.EventListener;
import com.djembefx.model.event.PlayNoteEvent;
import com.djembefx.render.SoundRenderer;
import com.google.inject.Inject;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:43
 */
public class SoundRendererInitializer {

    @Inject
    EventBus eventBus;

    @Inject
    SoundRenderer soundRenderer;

    public void init() {
        eventBus.addEventListener(PlayNoteEvent.class, new EventListener<PlayNoteEvent>() {
            @Override
            public void onEvent(PlayNoteEvent event) {
                soundRenderer.render(event.getNote(), event.getInstrument());
            }
        });
    }

}
