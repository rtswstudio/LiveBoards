package com.rtsw.liveboards.model;

import java.io.Serializable;

public class Label implements Serializable {

    private String table;

    private String column;

    public Label() {
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
