package com.djembefx.render;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;
import com.google.inject.ImplementedBy;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:43
 */
@ImplementedBy(SoundRendererImpl.class)
public interface SoundRenderer {

    public void render(Note note, Instrument instrument);

}
