package com.djembefx.application;

import com.djembefx.model.*;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 10/07/12
 * Time: 06:26
 */
public class Demo {

    public static Song createDemoSong() {
        Song song = new Song();
        song.setTitle("Demo");
        Loop loop = new Loop();
        loop.setName("Djembe 1");
        loop.setLength(128);
        loop.setInstrument(new Djembe());
        loop.getNotes().put(0L, new Note(DjembeType.SLAP));
        loop.getNotes().put(48L, new Note(DjembeType.SLAP));
        loop.getNotes().put(64L, new Note(DjembeType.SLAP));
        loop.getNotes().put(96L, new Note(DjembeType.TONE));
        loop.getNotes().put(112L, new Note(DjembeType.TONE));
        Loop loop1 = new Loop();
        loop1.setName("Djembe 2");
        loop1.setLength(128);
        loop1.setInstrument(new Djembe());
        loop1.getNotes().put(0L, new Note(DjembeType.OPEN));
        loop1.getNotes().put(32L, new Note(DjembeType.TONE));
        loop1.getNotes().put(48L, new Note(DjembeType.TONE));
        loop1.getNotes().put(96L, new Note(DjembeType.SLAP));
        song.getLoops().addAll(loop, loop1);
        return song;
    }

}
