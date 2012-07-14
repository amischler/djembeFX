package com.djembefx.model.instrument.impl;

import com.djembefx.model.NoteKind;
import com.djembefx.model.instrument.DjembeType;
import com.djembefx.model.instrument.Instrument;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 17:45
 */
public abstract class AbstractDjembe implements Instrument<DjembeType> {

    @Override
    public String getClip(NoteKind<DjembeType> noteKind) {
        if (noteKind.equals(DjembeType.BAS)) {
            return getOpen();
        } else if (noteKind.equals(DjembeType.SLAP)) {
            return getSlap();
        } else if (noteKind.equals(DjembeType.TONE)) {
            return getTone();
        }
        throw new IllegalArgumentException("This instrument can not play this note kind " + noteKind);
    }

    protected abstract String getTone();

    protected abstract String getSlap();

    protected abstract String getOpen();

}
