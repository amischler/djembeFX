package com.djembefx.model;

import com.djembefx.render.SoundRendererImpl;
import javafx.scene.media.AudioClip;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 15:24
 */
public class Djembe implements Instrument<DjembeType> {

    private final AudioClip toneClip = new AudioClip(SoundRendererImpl.class.getClassLoader().getResource("com/djembefx/media/D2_U.wav").toExternalForm());

    private final AudioClip openClip = new AudioClip(SoundRendererImpl.class.getClassLoader().getResource("com/djembefx/media/D2_O.wav").toExternalForm());

    private final AudioClip slapClip = new AudioClip(SoundRendererImpl.class.getClassLoader().getResource("com/djembefx/media/D2_V.wav").toExternalForm());


    @Override
    public AudioClip getClip(NoteKind<DjembeType> noteKind) {
        if (noteKind == DjembeType.OPEN) {
            return openClip;
        } else if (noteKind == DjembeType.SLAP) {
            return slapClip;
        } else if (noteKind == DjembeType.TONE) {
            return toneClip;
        }
        throw new IllegalArgumentException("This instrument can not play this note kind " + noteKind);
    }
}
