package com.djembefx.model;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 17:27
 */
public class AbstractNoteKind<InstrumentType> implements NoteKind<InstrumentType> {

    private final String name;

    public AbstractNoteKind(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractNoteKind that = (AbstractNoteKind) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
