package com.rtsw.liveboards.board.aggregation;

import java.util.HashSet;
import java.util.Set;

public class DistinctAggregation implements Aggregation {

    private Set<Object> set = new HashSet<>();

    private Integer value;

    public DistinctAggregation(Object value) {
        set.add(value);
    }

    @Override
    public void aggregate(Object another) {
        set.add(another);
    }

    @Override
    public Object value() {
        return (set.size());
    }

}
