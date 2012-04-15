package com.djembefx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 18:42
 */
public class Note {

    private final ObjectProperty<NoteKind> noteKind = new SimpleObjectProperty<NoteKind>();

    public Note(NoteKind noteKind) {
        setNoteKind(noteKind);
    }

    private void setNoteKind(NoteKind noteKind) {
        this.noteKind.set(noteKind);
    }

    public NoteKind getNoteKind() {
        return noteKind.get();
    }

    public ObjectProperty<NoteKind> noteKindProperty() {
        return noteKind;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteKind=" + noteKind.get() +
                '}';
    }
}
