package com.djembefx.model;

import javafx.scene.media.AudioClip;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:44
 */
public interface Instrument<T extends InstrumentType> {

    /**
     * Return the audio clip associated with this intrument for the given note kind.
     *
     * @param noteKind
     * @return
     */
    public AudioClip getClip(NoteKind<T> noteKind);


}
