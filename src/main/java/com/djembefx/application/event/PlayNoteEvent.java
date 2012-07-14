package com.djembefx.application.event;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/05/12
 * Time: 20:33
 */
public class PlayNoteEvent implements Event {

    private final Note note;

    private final Instrument instrument;

    public PlayNoteEvent(Note note, Instrument instrument) {
        this.note = note;
        this.instrument = instrument;
    }

    public Note getNote() {
        return note;
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
