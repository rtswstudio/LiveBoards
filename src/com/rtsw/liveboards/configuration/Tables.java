package com.rtsw.liveboards.configuration;

import java.io.Serializable;

public class Tables implements Serializable {

    private String path;

    public Tables() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
