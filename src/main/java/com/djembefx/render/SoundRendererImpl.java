package com.djembefx.render;

import com.djembefx.model.Instrument;
import com.djembefx.model.Note;
import javafx.scene.media.AudioClip;

import java.util.*;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 10:45
 */
public class SoundRendererImpl implements SoundRenderer {

    private Map<String, List<AudioClip>> cache = new HashMap<String, List<AudioClip>>();

    @Override
    public void render(Note note, Instrument instrument) {
        System.out.println("Rendering" + note + " @" + System.nanoTime());
        getOrCreateAudioClip(instrument.getClip(note.getNoteKind())).play(0.5);
    }

    private AudioClip getOrCreateAudioClip(String url) {
        if (!cache.containsKey(url)) {
            cache.put(url, new LinkedList<AudioClip>());
        }
        for (AudioClip audioClip : cache.get(url)) {
            return audioClip;
        }
        AudioClip audioClip = new AudioClip(url);
        cache.get(url).add(audioClip);
        return audioClip;

    }

}
