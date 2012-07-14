package com.djembefx.render;

import com.djembefx.model.instrument.Instrument;
import com.djembefx.model.Note;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 24/06/12
 * Time: 19:46
 */
public class LegacySoundRendererImpl implements SoundRenderer {
    private Map<String, Clip> cache = new HashMap<String, Clip>();

    @Override
    public void render(Note note, Instrument instrument) {
        Clip clip = getOrCreateAudioClip(instrument.getClip(note.getNoteKind()));
        clip.setFramePosition(0);
        clip.start();
    }

    private Clip getOrCreateAudioClip(String soundFileName) {
        if (cache.containsKey(soundFileName)) {
            return cache.get(soundFileName);
        }
        try {
            Clip clip;
            // Use URL (instead of File) to read from disk and JAR.
            URL url = new URL(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
            cache.put(soundFileName, clip);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return cache.get(soundFileName);

    }


}
