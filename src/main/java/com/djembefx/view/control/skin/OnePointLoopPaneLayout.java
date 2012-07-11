package com.djembefx.view.control.skin;

import com.djembefx.model.pulse.Pulser;
import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPaneLayout;
import com.google.inject.Inject;
import javafx.beans.binding.DoubleBinding;

import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 01/07/12
 * Time: 20:09
 */
public class OnePointLoopPaneLayout extends AbstractLoopPaneLayout implements LoopPaneLayout {

    @Inject
    Pulser pulser;

    @Override
    public void layout(List<LoopControl> loopControls) {
        for (final LoopControl loopControl : loopControls) {
            final double radius = 100;
            updateRadius(loopControl, radius);
            final Double angle = Math.random() * Math.PI * 2;
            updateTranslate(loopControl, Math.sin(angle) * radius, -Math.cos(angle) * radius);
            updateStaticRotate(loopControl, (angle * 360 / (2 * Math.PI)) + 180);
            updateDynamicRotate(loopControl, new DoubleBinding() {
                {
                    bind(pulser.pulseProperty(), loopControl.getLoop().lengthProperty());
                }

                @Override
                protected double computeValue() {
                    return (((double) (pulser.getPulse() %
                            loopControl.getLoop().getLength())) /
                            ((double) loopControl.getLoop().getLength()) * 360d);
                }
            });
        }
    }

    public String toString() {
        return "1point";
    }

}
