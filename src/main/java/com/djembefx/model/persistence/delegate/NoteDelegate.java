package com.djembefx.model.persistence.delegate;

import com.djembefx.model.Note;
import com.djembefx.model.NoteKind;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:40
 */
public class NoteDelegate extends PersistenceDelegate {
    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        Note note = (Note) o;
        return new Expression(o,
                this.getClass(),
                "createNoteV1",
                new Object[]{note.getNoteKind()});
    }

    public static Note createNoteV1(NoteKind noteKind) {
        Note note = new Note(noteKind);
        return note;
    }
}
