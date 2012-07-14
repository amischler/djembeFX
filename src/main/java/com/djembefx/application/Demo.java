package com.djembefx.application;

import com.djembefx.model.*;
import com.djembefx.model.instrument.DunDunType;
import com.djembefx.model.instrument.impl.Djembe;
import com.djembefx.model.instrument.DjembeType;
import com.djembefx.model.instrument.impl.DunDunBa;
import com.djembefx.model.instrument.impl.KenKeni;
import com.djembefx.model.instrument.impl.Sangban;

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
        loop.setLength(64);
        loop.setInstrument(new Djembe());
        loop.getNotes().put(0L, new Note(DjembeType.SLAP));
        loop.getNotes().put(24L, new Note(DjembeType.SLAP));
        loop.getNotes().put(32L, new Note(DjembeType.SLAP));
        loop.getNotes().put(48L, new Note(DjembeType.TONE));
        loop.getNotes().put(56L, new Note(DjembeType.TONE));

        Loop loop1 = new Loop();
        loop1.setName("Djembe 2");
        loop1.setLength(64);
        loop1.setInstrument(new Djembe());
        loop1.getNotes().put(0L, new Note(DjembeType.BAS));
        loop1.getNotes().put(16L, new Note(DjembeType.TONE));
        loop1.getNotes().put(24L, new Note(DjembeType.TONE));
        loop1.getNotes().put(48L, new Note(DjembeType.SLAP));

        Loop loop2 = new Loop();
        loop2.setName("Sangban");
        loop2.setLength(128);
        loop2.setInstrument(new Sangban());
        loop2.getNotes().put(0L, new Note(DunDunType.MUFFLED));
        loop2.getNotes().put(16L, new Note(DunDunType.BELL));
        loop2.getNotes().put(32L, new Note(DunDunType.MUFFLED));
        loop2.getNotes().put(48L, new Note(DunDunType.BELL));
        loop2.getNotes().put(64L, new Note(DunDunType.MUFFLED));
        loop2.getNotes().put(80L, new Note(DunDunType.OPEN));
        loop2.getNotes().put(96L, new Note(DunDunType.OPEN));
        loop2.getNotes().put(112L, new Note(DunDunType.BELL));

        Loop loop3 = new Loop();
        loop3.setName("Dundunba");
        loop3.setLength(128);
        loop3.setInstrument(new DunDunBa());
        loop3.getNotes().put(0L, new Note(DunDunType.OPEN));
        loop3.getNotes().put(16L, new Note(DunDunType.BELL));
        loop2.getNotes().put(32L, new Note(DunDunType.BELL));
        loop3.getNotes().put(48L, new Note(DunDunType.OPEN));
        loop3.getNotes().put(64L, new Note(DunDunType.OPEN));
        loop3.getNotes().put(80L, new Note(DunDunType.BELL));
        loop3.getNotes().put(96L, new Note(DunDunType.BELL));
        loop3.getNotes().put(112L, new Note(DunDunType.OPEN));

        Loop loop4 = new Loop();
        loop4.setName("Kenkeni");
        loop4.setLength(128);
        loop4.setInstrument(new KenKeni());
        loop4.getNotes().put(0L, new Note(DunDunType.OPEN));
        loop4.getNotes().put(16L, new Note(DunDunType.BELL));
        loop4.getNotes().put(32L, new Note(DunDunType.OPEN));
        loop4.getNotes().put(48L, new Note(DunDunType.BELL));
        loop4.getNotes().put(64L, new Note(DunDunType.OPEN));
        loop4.getNotes().put(80L, new Note(DunDunType.BELL));
        loop4.getNotes().put(96L, new Note(DunDunType.OPEN));
        loop4.getNotes().put(112L, new Note(DunDunType.BELL));

        song.getLoops().addAll(loop, loop1, loop2, loop3, loop4);
        return song;
    }

}
