package com.djembefx.model.instrument;

import com.djembefx.model.NoteKind;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:44
 */
public interface Instrument<T extends InstrumentType> {

    /**
     * Return the audio clip associated with this instrument for the given note kind.
     *
     * @param noteKind
     * @return
     */
    public String getClip(NoteKind<T> noteKind);


}
