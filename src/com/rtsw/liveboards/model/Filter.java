package com.rtsw.liveboards.model;

import java.io.Serializable;
import java.util.Map;

public class Filter implements Serializable {

    private String type;

    Map<String, String> properties;

    public Filter() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

}
