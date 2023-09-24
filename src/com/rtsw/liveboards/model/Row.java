package com.rtsw.liveboards.model;

import java.io.Serializable;
import java.util.List;

public class Row implements Serializable {

    private List<Object> values;

    public Row() {
    }

    public Row(List<Object> values) {
        this.values = values;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

}
