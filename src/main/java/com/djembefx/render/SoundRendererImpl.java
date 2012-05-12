package com.djembefx.render;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;
import com.djembefx.model.NoteKind;
import javafx.scene.media.AudioClip;

import java.util.*;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:45
 */
public class SoundRendererImpl implements SoundRenderer {

    private Map<NoteKind, List<AudioClip>> map = new HashMap<NoteKind, List<AudioClip>>();

    @Override
    public void render(Note note, Instrument instrument) {
            getOrCreateAudioClip(instrument, note.getNoteKind()).play();
    }

    private AudioClip getOrCreateAudioClip(Instrument instrument, NoteKind noteKind) {
        if (!map.containsKey(noteKind)) {
            map.put(noteKind, new LinkedList<AudioClip>());
        }
        for (AudioClip audioClip : map.get(noteKind)) {
            if (!audioClip.isPlaying()) {
                return audioClip;
            }
        }
        System.out.println("Creating clip for " + noteKind);
        AudioClip audioClip = new AudioClip(instrument.getClip(noteKind));
        map.get(noteKind).add(audioClip);
        return audioClip;

    }

}
