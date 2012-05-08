package com.djembefx.model.persistence.delegate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.util.Arrays;
import java.util.List;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:33
 */
public class ObservableListDelegate extends PersistenceDelegate {

    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        List list = (List) o;
        return new Expression(o,
                this.getClass(),
                "createObservableListV1",
                new Object[]{list.toArray()});
    }

    public static ObservableList createObservableListV1(Object[] data) {
        return FXCollections.observableList(Arrays.asList(data));
    }
}
