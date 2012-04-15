package com.djembefx.model;

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

    }

    public static class Slap implements NoteKind<DjembeType> {

        public String toString() {
            return "Slap";
        }

    }

    public static class Open implements NoteKind<DjembeType> {

        public String toString() {
            return "Open";
        }

    }

    @Override
    public Collection<NoteKind> getAvailableNoteTypes() {
        return Arrays.asList(new NoteKind[]{TONE, SLAP, OPEN});
    }
}
