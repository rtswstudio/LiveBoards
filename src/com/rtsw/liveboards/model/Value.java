package com.rtsw.liveboards.model;

import java.io.Serializable;

public class Value implements Serializable {

    private String table;

    private String column;

    private String aggregate;

    private String category;

    public Value() {
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

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
