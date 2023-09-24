package com.rtsw.liveboards.test;

import com.rtsw.liveboards.board.filter.HeadFilter;
import com.rtsw.liveboards.board.filter.MatchFilter;
import com.rtsw.liveboards.board.filter.TailFilter;
import com.rtsw.liveboards.model.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Filter {

    public List<Row> rows() {
        List<Row> rows = new ArrayList<>();
        rows.add(new Row(List.of(0, 1, 2, 3, 4)));
        rows.add(new Row(List.of(5, 6, 7, 8, 9)));
        return (rows);
    }

    @Test
    public void head() {
        HeadFilter filter = new HeadFilter(1);
        List<Row> filtered = filter.filter(rows());
        Assertions.assertTrue(filtered.size() == 1);
        Assertions.assertTrue(Integer.parseInt(filtered.get(0).getValues().get(0).toString()) == 0);
    }

    @Test
    public void tail() {
        TailFilter filter = new TailFilter(1);
        List<Row> filtered = filter.filter(rows());
        Assertions.assertTrue(filtered.size() == 1);
        Assertions.assertTrue(Integer.parseInt(filtered.get(0).getValues().get(0).toString()) == 5);
    }

    @Test
    public void match_equals() {
        MatchFilter filter = new MatchFilter(2, 7, MatchFilter.OPERATION_EQUALS);
        List<Row> filtered = filter.filter(rows());
        Assertions.assertTrue(filtered.size() == 1);
        Assertions.assertTrue(Integer.parseInt(filtered.get(0).getValues().get(0).toString()) == 5);
    }

    @Test
    public void match_below() {
        MatchFilter filter = new MatchFilter(2, 5, MatchFilter.OPERATION_BELOW);
        List<Row> filtered = filter.filter(rows());
        Assertions.assertTrue(filtered.size() == 1);
        Assertions.assertTrue(Integer.parseInt(filtered.get(0).getValues().get(0).toString()) == 0);
    }

    @Test
    public void match_above() {
        MatchFilter filter = new MatchFilter(2, 5, MatchFilter.OPERATION_ABOVE);
        List<Row> filtered = filter.filter(rows());
        Assertions.assertTrue(filtered.size() == 1);
        Assertions.assertTrue(Integer.parseInt(filtered.get(0).getValues().get(0).toString()) == 5);
    }

}
