package com.rtsw.liveboards.board;

import com.rtsw.liveboards.board.filter.HeadFilter;
import com.rtsw.liveboards.board.filter.TailFilter;
import com.rtsw.liveboards.board.filter.MatchFilter;
import com.rtsw.liveboards.model.*;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class BoardFilter {

    private static Logger L = LogManager.getLogger(BoardFilter.class);

    private Vertx vertx;

    private Board board;

    public BoardFilter(Vertx vertx, Board board) {
        this.vertx = vertx;
        this.board = board;
    }

    public List<Row> filter() {
        long start = System.currentTimeMillis();

        ModelRepository modelRepository = new ModelRepository(vertx);
        List<Row> rows = modelRepository.listRows(board.getLabel().getTable());
        if (rows == null) {
            L.warn(String.format("no rows to filter from table '%s'", board.getLabel().getTable()));
            return (new ArrayList<>());
        }
        L.debug(String.format("filtering %d rows from table '%s'", rows.size(), board.getLabel().getTable()));

        for (Filter filter : board.getFilters()) {
            switch (filter.getType()) {
                case "head": {
                    int num = Integer.parseInt(filter.getProperties().get("value"));
                    HeadFilter headFilter = new HeadFilter(num);
                    rows = headFilter.filter(rows);
                    break;
                }
                case "tail": {
                    int num = Integer.parseInt(filter.getProperties().get("value"));
                    TailFilter tailFilter = new TailFilter(num);
                    rows = tailFilter.filter(rows);
                    break;
                }
                case "match": {
                    String table = filter.getProperties().get("table");
                    String column = filter.getProperties().get("column");
                    String value = filter.getProperties().get("value");
                    String operation = filter.getProperties().get("operation");
                    if (board.getLabel().getTable().equals(table)) {
                        int i = ModelUtils.columnIndex(modelRepository.getTable(table), column);
                        MatchFilter matchFilter = new MatchFilter(i, value, operation);
                        rows = matchFilter.filter(rows);
                    }
                    break;
                }
            }
        }

        L.debug(String.format("%d rows(s) left after filtering", rows.size()));

        long end = System.currentTimeMillis();
        L.debug(String.format("filtering completed in %d milliseconds", (end - start)));

        return (rows);
    }

}
