package com.djembefx.view.control.skin;

import com.djembefx.view.control.LoopControl;
import com.djembefx.view.control.LoopPaneLayout;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 01/07/12
 * Time: 20:49
 */
public abstract class AbstractLoopPaneLayout implements LoopPaneLayout {

    private static final Duration DURATION = new Duration(750);

    static Map<LoopControl, Translate> translateMap = new HashMap<LoopControl, Translate>();

    static Map<LoopControl, Rotate> staticRotateMap = new HashMap<LoopControl, Rotate>();

    static Map<LoopControl, Rotate> dynamicRotateMap = new HashMap<LoopControl, Rotate>();

    private void init(LoopControl loopControl) {
        if (!translateMap.containsKey(loopControl)) {
            Translate translate = new Translate();
            Rotate staticRotate = new Rotate();
            Rotate dynamicRotate = new Rotate();
            translateMap.put(loopControl, translate);
            staticRotateMap.put(loopControl, staticRotate);
            dynamicRotateMap.put(loopControl, dynamicRotate);
            loopControl.getTransforms().addAll(translate, dynamicRotate, staticRotate);
        }
    }

    protected void updateRadius(LoopControl loopControl, double radius) {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(loopControl.radiusProperty(), radius);
        KeyFrame keyFrame = new KeyFrame(DURATION, keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    protected void updateTranslate(LoopControl loopControl, double x, double y) {
        init(loopControl);
        Translate translate = translateMap.get(loopControl);
        Timeline timeline = new Timeline();
        KeyValue xKeyValue = new KeyValue(translate.xProperty(), x);
        KeyValue yKeyValue = new KeyValue(translate.yProperty(), y);
        KeyFrame keyFrame = new KeyFrame(DURATION, xKeyValue, yKeyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    protected void updateStaticRotate(LoopControl loopControl, double angle) {
        init(loopControl);
        Rotate rotate = staticRotateMap.get(loopControl);
        Timeline timeline = new Timeline();
        KeyValue angleKeyValue = new KeyValue(rotate.angleProperty(), angle);
        KeyFrame keyFrame = new KeyFrame(DURATION, angleKeyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    protected void updateDynamicRotate(LoopControl loopControl, DoubleBinding doubleBinding) {
        init(loopControl);
        dynamicRotateMap.get(loopControl).angleProperty().unbind();
        dynamicRotateMap.get(loopControl).angleProperty().bind(doubleBinding);
    }

}
