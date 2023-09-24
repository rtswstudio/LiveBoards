package com.rtsw.liveboards.board.filter;

import com.rtsw.liveboards.model.Row;

import java.util.List;

public interface Filter {

    List<Row> filter(List<Row> rows);

}
