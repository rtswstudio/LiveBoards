package com.rtsw.liveboards.board.aggregation;

public class CountAggregation implements Aggregation {

    private Integer value;

    public CountAggregation() {
        value = 1;
    }

    @Override
    public void aggregate(Object another) {
        value = value + 1;
    }

    @Override
    public Object value() {
        return (value);
    }

}
