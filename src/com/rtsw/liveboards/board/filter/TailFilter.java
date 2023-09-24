package com.rtsw.liveboards.board.filter;

import com.rtsw.liveboards.model.Row;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TailFilter implements Filter {

    private static Logger L = LogManager.getLogger(TailFilter.class);

    private int num;

    public TailFilter(int num) {
        this.num = num;
    }

    @Override
    public List<Row> filter(List<Row> rows) {
        rows = rows.subList(Math.max(rows.size() - num, 0), rows.size());
        L.debug(String.format("number of rows after 'tail' filter: %d", rows.size()));
        return (rows);
    }

}
