package com.rtsw.liveboards.configuration;

import java.io.Serializable;

public class Server implements Serializable {

    private String host;

    private int port;

    public Server() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
