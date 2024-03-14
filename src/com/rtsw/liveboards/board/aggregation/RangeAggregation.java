package com.rtsw.liveboards.board.aggregation;

import java.util.Date;
import java.util.Set;

public class RangeAggregation implements Aggregation {

    private Object min;

    private Object max;

    private Object value;

    public static Set<Class> ALLOWED_CLASSES = Set.of(Integer.class, Double.class, Date.class);

    public RangeAggregation(Object value) {
        min = value;
        max = value;
        if (value instanceof Double) {
            this.value = 0.0;
        }
        if (value instanceof Integer) {
            this.value = 0;
        }
        if (value instanceof Date) {
            this.value = 0;
        }
    }

    @Override
    public void aggregate(Object another) {
        if (value instanceof Double && another instanceof Double) {
            Double a = (Double) min;
            Double b = (Double) max;
            Double c = (Double) another;
            if (c < a) {
                min = c;
            }
            if (c > b) {
                max = c;
            }
            value = (Double) max - (Double) min;
        }
        if (value instanceof Integer && another instanceof Integer) {
            Integer a = (Integer) min;
            Integer b = (Integer) max;
            Integer c = (Integer) another;
            if (c < a) {
                min = c;
            }
            if (c > b) {
                max = c;
            }
            value = (Integer) max - (Integer) min;
        }
        if (value instanceof Date && another instanceof Date) {
            Date a = (Date) min;
            Date b = (Date) max;
            Date c = (Date) another;
            if (c.before(a)) {
                min = c;
            }
            if (c.after(b)) {
                max = c;
            }
            value = ((Date) max).getTime() - ((Date) min).getTime();
        }
    }

    @Override
    public Object value() {
        return (value);
    }

}
