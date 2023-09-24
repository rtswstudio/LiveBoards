package com.rtsw.liveboards.board.filter;

import com.rtsw.liveboards.model.Row;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HeadFilter implements Filter {

    private static Logger L = LogManager.getLogger(HeadFilter.class);

    private int num;

    public HeadFilter(int num) {
        this.num = num;
    }

    @Override
    public List<Row> filter(List<Row> rows) {
        rows = rows.subList(0, Math.min(rows.size(), num));
        L.debug(String.format("number of rows after 'head' filter: %d", rows.size()));
        return (rows);
    }

}
