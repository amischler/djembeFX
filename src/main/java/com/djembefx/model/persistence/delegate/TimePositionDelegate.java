package com.djembefx.model.persistence.delegate;

import com.djembefx.model.TimePosition;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.math.BigInteger;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 08/05/12
 * Time: 17:39
 */
public class TimePositionDelegate extends PersistenceDelegate {
    @Override
    protected Expression instantiate(Object o, Encoder encoder) {
        TimePosition timePosition = (TimePosition) o;
        return new Expression(o,
                this.getClass(),
                "createTimePositionV1",
                new Object[]{timePosition.getPosition().toString()});
    }

    public static TimePosition createTimePositionV1(String bigInteger) {
        TimePosition timePosition = new TimePosition(new BigInteger(bigInteger));
        return timePosition;
    }
}
