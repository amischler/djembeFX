package com.djembefx.view.control.skin;

import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPaneLayout;
import javafx.beans.binding.DoubleBinding;

import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 01/07/12
 * Time: 20:08
 */
public class FlowLoopPaneLayout extends AbstractLoopPaneLayout implements LoopPaneLayout {
    @Override
    public void layout(List<LoopControl> loopControls) {
        int i = 0;
        for (final LoopControl loopControl : loopControls) {
            updateRadius(loopControl, 50 + i * 20);
            updateTranslate(loopControl, 0, 0);
            updateStaticRotate(loopControl, 0);
            updateDynamicRotate(loopControl, new DoubleBinding() {
                @Override
                protected double computeValue() {
                    return 0;
                }
            });
            loopControl.toBack();
            i++;
        }
    }

    public String toString() {
        return "Flow";
    }
}
