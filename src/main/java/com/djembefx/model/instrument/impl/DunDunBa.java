package com.djembefx.model.instrument.impl;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 17:59
 */
public class DunDunBa extends AbstractDunDun {

    private final String openClip = DunDunBa.class.getClassLoader().getResource("com/djembefx/media/dundun/D_M.wav").toExternalForm();

    private final String muffledClip = DunDunBa.class.getClassLoader().getResource("com/djembefx/media/dundun/D_O.wav").toExternalForm();

    private final String bellClip = DunDunBa.class.getClassLoader().getResource("com/djembefx/media/dundun/G_D_O.wav").toExternalForm();

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
