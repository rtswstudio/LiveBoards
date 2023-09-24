package com.rtsw.liveboards.board.aggregation;

import java.util.Set;

public class MaximumAggregation implements Aggregation {

    private Object value;

    public static Set<Class> ALLOWED_CLASSES = Set.of(Integer.class, Double.class);

    public MaximumAggregation(Object value) {
        this.value = value;
    }

    @Override
    public void aggregate(Object another) {
        if (value instanceof Double && another instanceof Double) {
            Double a = (Double) value;
            Double b = (Double) another;
            if (b > a) {
                value = b;
            }
        }
        if (value instanceof Integer && another instanceof Integer) {
            Integer a = (Integer) value;
            Integer b = (Integer) another;
            if (b > a) {
                value = b;
            }
        }
    }

    @Override
    public Object value() {
        return (value);
    }

}
