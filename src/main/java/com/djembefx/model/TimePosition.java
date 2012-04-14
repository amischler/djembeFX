package com.djembefx.model;

import java.math.BigInteger;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 14/04/12
 * Time: 20:01
 */
public class TimePosition {

    private final BigInteger position;

    public TimePosition(int position) {
        this.position = new BigInteger(Integer.toString(position));
    }

    public TimePosition(BigInteger position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimePosition that = (TimePosition) o;

        if (!position.equals(that.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    public BigInteger getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "TimePosition{" +
                "position=" + position +
                '}';
    }
}
