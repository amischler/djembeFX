package com.djembefx.render;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:45
 */
public class SoundRendererImpl implements SoundRenderer {


    @Override
    public void render(Note note, Instrument instrument) {
        System.out.println(note + "@" + System.currentTimeMillis());
        instrument.getClip(note.getNoteKind()).play();
    }

}
