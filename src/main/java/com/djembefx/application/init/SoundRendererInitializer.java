package com.djembefx.application.init;

import com.djembefx.application.event.EventBus;
import com.djembefx.application.event.EventListener;
import com.djembefx.application.event.PlayNoteEvent;
import com.djembefx.render.SoundRenderer;
import com.google.inject.Inject;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:43
 */
public class SoundRendererInitializer implements Initializer {

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
