package com.rtsw.liveboards.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtsw.liveboards.board.BoardRepository;
import com.rtsw.liveboards.model.ModelRepository;
import com.rtsw.liveboards.configuration.ConfigurationRepository;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 */
public class Server {

    private static Logger L = LogManager.getLogger(Server.class);

    private static String configFile = "liveboards-config.json";

    public static void main(String[] args) {

        // config file
        String configFile = System.getProperty("config.file", "liveboards-config.json");

        // search for configuration
        try {
            File file = new File(configFile);
            ConfigurationRepository.getRepository().load(file);
        } catch (Exception e) {
            L.warn(String.format("cannot load configuration from file '%s'", configFile));
            return;
        }

        // init vertx
        Vertx vertx = Vertx.vertx();

        // search for tables
        ModelRepository modelRepository = new ModelRepository(vertx);
        File tables = new File(ConfigurationRepository.getRepository().get().getTables().getPath());
        if (tables.isDirectory() && tables.canRead()) {
            for (File file : tables.listFiles(new JSONFilenameFilter())) {
                try {
                    modelRepository.createTable(new ObjectMapper().readTree(file));
                } catch (Exception e) {
                    L.warn(String.format("cannot load table from file '%s: %s'", file.getAbsolutePath(), e.getMessage()));
                }
            }
        } else {
            L.warn(String.format("cannot read directory '%s' for tables", tables.getAbsolutePath()));
        }

        // search for boards
        BoardRepository boardRepository = new BoardRepository(vertx);
        File boards = new File(ConfigurationRepository.getRepository().get().getBoards().getPath());
        if (boards.isDirectory() && boards.canRead()) {
            for (File file : boards.listFiles(new JSONFilenameFilter())) {
                try {
                    boardRepository.create(new ObjectMapper().readTree(file));
                } catch (Exception e) {
                    L.warn(String.format("cannot load board from file '%s: %s'", file.getAbsolutePath(), e.getMessage()));
                }
            }
        } else {
            L.warn(String.format("cannot read directory '%s' for boards", boards.getAbsolutePath()));
        }

        vertx.deployVerticle(new ServerVerticle());
        L.info("deployed server verticle - press CTRL-C in console to stop");
    }

    private static class JSONFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return (name.toLowerCase().endsWith(".json"));
        }

    }

}
