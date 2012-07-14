package com.djembefx.model.instrument.impl;

import com.djembefx.model.NoteKind;
import com.djembefx.model.instrument.DjembeType;
import com.djembefx.model.instrument.Instrument;
import com.djembefx.render.SoundRendererImpl;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 15:24
 */
public class Djembe implements Instrument<DjembeType> {

    private final String toneClip = SoundRendererImpl.class.getClassLoader().getResource("com/djembefx/media/D2_U.wav").toExternalForm();

    private final String openClip = SoundRendererImpl.class.getClassLoader().getResource("com/djembefx/media/D2_O.wav").toExternalForm();

    private final String slapClip = SoundRendererImpl.class.getClassLoader().getResource("com/djembefx/media/D2_V.wav").toExternalForm();


    @Override
    public String getClip(NoteKind<DjembeType> noteKind) {
        if (noteKind.getClass() == DjembeType.OPEN.getClass()) {
            return openClip;
        } else if (noteKind.getClass() == DjembeType.SLAP.getClass()) {
            return slapClip;
        } else if (noteKind.getClass() == DjembeType.TONE.getClass()) {
            return toneClip;
        }
        throw new IllegalArgumentException("This instrument can not play this note kind " + noteKind);
    }
}
