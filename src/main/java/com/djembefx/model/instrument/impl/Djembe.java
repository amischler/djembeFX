package com.djembefx.model.instrument.impl;

import com.djembefx.model.instrument.DjembeType;
import com.djembefx.model.instrument.Instrument;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 15/04/12
 * Time: 15:24
 */
public class Djembe extends AbstractDjembe implements Instrument<DjembeType> {

    private final String toneClip = Djembe.class.getClassLoader().getResource("com/djembefx/media/djembe/D2_U.wav").toExternalForm();

    private final String openClip = Djembe.class.getClassLoader().getResource("com/djembefx/media/djembe/D2_O.wav").toExternalForm();

    private final String slapClip = Djembe.class.getClassLoader().getResource("com/djembefx/media/djembe/D2_V.wav").toExternalForm();


    @Override
    protected String getTone() {
        return toneClip;
    }

    @Override
    protected String getSlap() {
        return slapClip;
    }

    @Override
    protected String getOpen() {
        return openClip;
    }
}
