package com.djembefx.model.instrument;

import com.djembefx.model.AbstractNoteKind;
import com.djembefx.model.NoteKind;

import java.util.Arrays;
import java.util.Collection;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 15:28
 */
public class DjembeType implements InstrumentType {

    public final static NoteKind<DjembeType> TONE = new AbstractNoteKind<DjembeType>("Tone");

    public final static NoteKind<DjembeType> SLAP = new AbstractNoteKind<DjembeType>("Slap");

    public final static NoteKind<DjembeType> BAS = new AbstractNoteKind<DjembeType>("Bas");

    @Override
    public Collection<NoteKind> getAvailableNoteTypes() {
        return Arrays.asList(new NoteKind[]{TONE, SLAP, BAS});
    }
}
