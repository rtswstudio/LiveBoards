package com.rtsw.liveboards.board.aggregation;

public class LastAggregation implements Aggregation {

    private Object value;

    public LastAggregation(Object value) {
        this.value = value;
    }

    @Override
    public void aggregate(Object another) {
        value = another;
    }

    @Override
    public Object value() {
        return (value);
    }

}
