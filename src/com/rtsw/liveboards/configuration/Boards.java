package com.rtsw.liveboards.configuration;

import java.io.Serializable;

public class Boards implements Serializable {

    private String path;

    public Boards() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
