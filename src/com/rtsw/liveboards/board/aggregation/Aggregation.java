package com.rtsw.liveboards.board.aggregation;

public interface Aggregation {

    void aggregate(Object another);

    Object value();

}
