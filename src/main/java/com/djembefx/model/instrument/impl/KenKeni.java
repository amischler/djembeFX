package com.djembefx.model.instrument.impl;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/07/12
 * Time: 18:00
 */
public class KenKeni extends AbstractDunDun {

    private final String openClip = KenKeni.class.getClassLoader().getResource("com/djembefx/media/dundun/K_M.wav").toExternalForm();

    private final String muffledClip = KenKeni.class.getClassLoader().getResource("com/djembefx/media/dundun/K_O.wav").toExternalForm();

    private final String bellClip = KenKeni.class.getClassLoader().getResource("com/djembefx/media/dundun/G_K_O.wav").toExternalForm();

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
