package com.djembefx.application.handler;

import com.djembefx.application.event.DeleteNoteEvent;
import com.djembefx.model.Note;
import javafx.collections.ObservableMap;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 12/07/12
 * Time: 23:29
 */
public class DeleteNoteHandler implements Handler<DeleteNoteEvent> {

    @Override
    public Class<DeleteNoteEvent> getEventClass() {
        return DeleteNoteEvent.class;
    }

    @Override
    public void handle(DeleteNoteEvent event) {
        ObservableMap<Long, Note> notes = event.getLoop().getNotes();
        if (notes.containsValue(event.getNote())) {
            for (Long time : notes.keySet()) {
                if (notes.get(time) == event.getNote()) {
                    notes.remove(time);
                    return;
                }
            }
        }
    }

}
