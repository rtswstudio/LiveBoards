package com.rtsw.liveboards.board.filter;

import com.rtsw.liveboards.model.Row;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class MatchFilter implements Filter {

    public static final String OPERATION_EQUALS = "equals";

    public static final String OPERATION_EQUALS_OR_BELOW = "equals_on_below";

    public static final String OPERATION_BELOW = "below";

    public static final String OPERATION_EQUALS_OR_ABOVE = "equals_or_above";

    public static final String OPERATION_ABOVE = "above";

    private static Logger L = LogManager.getLogger(MatchFilter.class);

    private int column;

    private Object value;

    private String operation;

    public MatchFilter(int column, Object value, String operation) {
        this.column = column;
        this.value = value;
        this.operation = operation;
    }

    @Override
    public List<Row> filter(List<Row> rows) {
        for (Iterator<Row> iterator = rows.iterator(); iterator.hasNext(); ) {
            Row row = iterator.next();
            switch (operation) {
                case OPERATION_EQUALS: {
                    if (!value.equals(row.getValues().get(column))) {
                        iterator.remove();
                    }
                    break;
                }
                case OPERATION_EQUALS_OR_BELOW: {
                    Object o = row.getValues().get(column);
                    if (o instanceof Double && !((Double) o <= Double.parseDouble(value.toString()))) {
                        iterator.remove();
                    }
                    if (o instanceof Integer && !((Integer) o <= Integer.parseInt(value.toString()))) {
                        iterator.remove();
                    }
                    break;
                }
                case OPERATION_BELOW: {
                    Object o = row.getValues().get(column);
                    if (o instanceof Double && !((Double) o < Double.parseDouble(value.toString()))) {
                        iterator.remove();
                    }
                    if (o instanceof Integer && !((Integer) o < Integer.parseInt(value.toString()))) {
                        iterator.remove();
                    }
                    break;
                }
                case OPERATION_EQUALS_OR_ABOVE: {
                    Object o = row.getValues().get(column);
                    if (o instanceof Double && !((Double) o >= Double.parseDouble(value.toString()))) {
                        iterator.remove();
                    }
                    if (o instanceof Integer && !((Integer) o >= Integer.parseInt(value.toString()))) {
                        iterator.remove();
                    }
                    break;
                }
                case OPERATION_ABOVE: {
                    Object o = row.getValues().get(column);
                    if (o instanceof Double && !((Double) o > Double.parseDouble(value.toString()))) {
                        iterator.remove();
                    }
                    if (o instanceof Integer && !((Integer) o > Integer.parseInt(value.toString()))) {
                        iterator.remove();
                    }
                    break;
                }
            }
        }
        L.debug(String.format("number of rows after 'match' filter: %d", rows.size()));
        return (rows);
    }

}
