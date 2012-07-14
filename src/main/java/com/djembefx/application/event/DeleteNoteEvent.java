package com.djembefx.application.event;

import com.djembefx.model.Loop;
import com.djembefx.model.Note;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/07/12
 * Time: 23:24
 */
public class DeleteNoteEvent implements Event {

    private final Note note;

    private final Loop loop;

    public DeleteNoteEvent(Note note, Loop loop) {
        this.note = note;
        this.loop = loop;
    }

    public Note getNote() {
        return note;
    }

    public Loop getLoop() {
        return loop;
    }
}
