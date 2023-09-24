package com.rtsw.liveboards.board.aggregation;

import java.util.SortedMap;
import java.util.TreeMap;

public class ModeAggregation implements Aggregation {

    private SortedMap<Object, Integer> frequency = new TreeMap<>();

    public ModeAggregation(Object value) {
        frequency.put(value, 1);
    }

    @Override
    public void aggregate(Object another) {
        if (frequency.containsKey(another)) {
            frequency.put(another, frequency.get(another) + 1);
        } else {
            frequency.put(another, 1);
        }
    }

    @Override
    public Object value() {
        return (frequency.firstKey());
    }

}
