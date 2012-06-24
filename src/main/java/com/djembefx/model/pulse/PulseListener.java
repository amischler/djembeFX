package com.djembefx.model.pulse;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 24/06/12
 * Time: 19:12
 */
public interface PulseListener {

    /**
     * This method is called every pulse.
     */
    public void pulse(long pulse);

}
