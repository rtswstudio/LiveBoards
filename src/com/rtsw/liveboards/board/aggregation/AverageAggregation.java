package com.rtsw.liveboards.board.aggregation;

import java.util.Set;

public class AverageAggregation implements Aggregation {

    private int count;

    private double sum;

    private Double value;

    public static Set<Class> ALLOWED_CLASSES = Set.of(Integer.class, Double.class);

    public AverageAggregation(Object value) {
        count = 1;
        if (value instanceof Double) {
            sum = (Double) value;
        }
        if (value instanceof Integer) {
            sum = Double.valueOf((Integer) value);
        }
        this.value = sum;
    }

    @Override
    public void aggregate(Object another) {
        count = count + 1;
        if (another instanceof Double) {
            sum = sum + (Double) another;
        }
        if (another instanceof Integer) {
            sum = sum + Double.valueOf((Integer) another);
        }
        value = sum / count * 1.0;
    }

    @Override
    public Object value() {
        return (value);
    }

}
