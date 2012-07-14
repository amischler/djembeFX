package com.djembefx.model.instrument.impl;

import com.djembefx.model.instrument.DunDunType;
import com.djembefx.model.instrument.Instrument;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 17:25
 */
public class Sangban extends AbstractDunDun implements Instrument<DunDunType> {

    private final String openClip = Sangban.class.getClassLoader().getResource("com/djembefx/media/dundun/S_M.wav").toExternalForm();

    private final String muffledClip = Sangban.class.getClassLoader().getResource("com/djembefx/media/dundun/S_O.wav").toExternalForm();

    private final String bellClip = Sangban.class.getClassLoader().getResource("com/djembefx/media/dundun/G_S_O.wav").toExternalForm();


    @Override
    protected String getBell() {
        return bellClip;
    }

    @Override
    protected String getMuffled() {
        return muffledClip;
    }

    @Override
    protected String getOpen() {
        return openClip;
    }
}
