package com.rtsw.liveboards.model;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable {

    private String name;

    private List<Column> columns;

    private int capacity = -1;

    public Table() {
    }

    public Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
