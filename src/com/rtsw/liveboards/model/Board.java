package com.rtsw.liveboards.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Board implements Serializable {

    private String name;

    private String type;

    private Label label;

    private List<Value> values;

    private List<Filter> filters;

    private Map<String, Object> options;

    private String title;

    public Board() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
