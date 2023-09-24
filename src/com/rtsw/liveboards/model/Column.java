package com.rtsw.liveboards.model;

import java.io.Serializable;

public class Column implements Serializable {

    public static final String TYPE_INTEGER = "integer";

    public static final String TYPE_DOUBLE = "double";

    public static final String TYPE_STRING = "string";

    public static final String TYPE_BOOLEAN = "boolean";

    public static final String TYPE_DATE = "date";

    private String name;

    private String type;

    public Column() {
    }

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
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

}
