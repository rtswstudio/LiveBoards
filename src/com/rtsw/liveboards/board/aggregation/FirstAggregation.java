package com.rtsw.liveboards.board.aggregation;

public class FirstAggregation implements Aggregation {

    private Object value;

    public FirstAggregation(Object value) {
        this.value = value;
    }

    @Override
    public void aggregate(Object another) {
    }

    @Override
    public Object value() {
        return (value);
    }

}
