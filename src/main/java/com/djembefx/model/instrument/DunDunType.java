package com.djembefx.model.instrument;

import com.djembefx.model.AbstractNoteKind;
import com.djembefx.model.NoteKind;

import java.util.Arrays;
import java.util.Collection;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 17:23
 */
public class DunDunType implements InstrumentType {

    public final static NoteKind<DunDunType> OPEN = new AbstractNoteKind<DunDunType>("Open");

    public final static NoteKind<DunDunType> MUFFLED = new AbstractNoteKind<DunDunType>("Muffled");

    public final static NoteKind<DunDunType> BELL = new AbstractNoteKind<DunDunType>("Bell");


    @Override
    public Collection<NoteKind> getAvailableNoteTypes() {
        return Arrays.asList(new NoteKind[]{OPEN, MUFFLED, BELL});
    }

}
