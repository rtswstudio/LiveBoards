package com.rtsw.liveboards.model;

public class ModelUtils {

    public static int columnIndex(Table table, String columnName) {
        int index = 0;
        for (Column column : table.getColumns()) {
            if (column.getName().equals(columnName)) {
                return (index);
            }
            index++;
        }
        return (-1);
    }

    public static Column column(Table table, String columnName) {
        for (Column column : table.getColumns()) {
            if (column.getName().equals(columnName)) {
                return (column);
            }
        }
        return (null);
    }

}
