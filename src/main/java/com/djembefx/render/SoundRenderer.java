package com.djembefx.render;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;
import com.google.inject.ImplementedBy;

import java.util.Collection;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:43
 */
@ImplementedBy(SoundRendererImpl.class)
public interface SoundRenderer {

    public void render(Collection<Note> note, Instrument instrument);

}
