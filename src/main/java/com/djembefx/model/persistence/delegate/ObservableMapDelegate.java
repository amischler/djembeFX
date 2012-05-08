package com.djembefx.model.persistence.delegate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:41
 */
public class ObservableMapDelegate extends PersistenceDelegate {

    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        ObservableMap observableMap = (ObservableMap) o;
        HashMap data = new HashMap();
        data.putAll(observableMap);
        return new Expression(o,
                this.getClass(),
                "createObservableMapV1",
                new Object[]{data});
    }

    public static ObservableMap createObservableMapV1(Map map) {
        return FXCollections.observableMap(map);
    }
}
