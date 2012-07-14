package com.djembefx.model.instrument.impl;

import com.djembefx.model.NoteKind;
import com.djembefx.model.instrument.DunDunType;
import com.djembefx.model.instrument.Instrument;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 17:51
 */
public abstract class AbstractDunDun implements Instrument<DunDunType> {

    @Override
    public String getClip(NoteKind<DunDunType> dunDunTypeNoteKind) {
        if (dunDunTypeNoteKind.equals(DunDunType.OPEN)) {
            return getOpen();
        } else if (dunDunTypeNoteKind.equals(DunDunType.MUFFLED)) {
            return getMuffled();
        } else if (dunDunTypeNoteKind.equals(DunDunType.BELL)) {
            return getBell();
        }
        throw new IllegalArgumentException("This instrument can not play this note kind " + dunDunTypeNoteKind);
    }

    protected abstract String getBell();

    protected abstract String getMuffled();

    protected abstract String getOpen();
}
