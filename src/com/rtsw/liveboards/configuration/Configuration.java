package com.rtsw.liveboards.configuration;

import java.io.Serializable;

public class Configuration implements Serializable {

    private Server server;

    private Tables tables;

    private Boards boards;

    public Configuration() {
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public Boards getBoards() {
        return boards;
    }

    public void setBoards(Boards boards) {
        this.boards = boards;
    }

}
