package com.djembefx.render;

import com.djembefx.model.instrument.Instrument;
import com.djembefx.model.Note;
import com.google.inject.ImplementedBy;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:43
 */
@ImplementedBy(LegacySoundRendererImpl.class)
public interface SoundRenderer {

    public void render(Note note, Instrument instrument);

}
