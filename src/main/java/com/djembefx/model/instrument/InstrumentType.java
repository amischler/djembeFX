package com.djembefx.model.instrument;

import com.djembefx.model.NoteKind;

import java.util.Collection;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 15:27
 */
public interface InstrumentType {

    public Collection<NoteKind> getAvailableNoteTypes();

}
