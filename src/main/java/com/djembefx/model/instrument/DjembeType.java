package com.djembefx.model.instrument;

import com.djembefx.model.NoteKind;

import java.util.Arrays;
import java.util.Collection;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 15:28
 */
public class DjembeType implements InstrumentType {

    public final static NoteKind<DjembeType> TONE = new Tone();

    public final static NoteKind<DjembeType> SLAP = new Slap();

    public final static NoteKind<DjembeType> OPEN = new Open();


    public static class Tone implements NoteKind<DjembeType> {

        public String toString() {
            return "Tone";
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        public boolean equals(Object o) {
            return getClass().equals(o.getClass());
        }

    }

    public static class Slap implements NoteKind<DjembeType> {

        public String toString() {
            return "Slap";
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        public boolean equals(Object o) {
            return getClass().equals(o.getClass());
        }

    }

    public static class Open implements NoteKind<DjembeType> {

        public String toString() {
            return "Open";
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        public boolean equals(Object o) {
            return getClass().equals(o.getClass());
        }

    }

    @Override
    public Collection<NoteKind> getAvailableNoteTypes() {
        return Arrays.asList(new NoteKind[]{TONE, SLAP, OPEN});
    }
}
