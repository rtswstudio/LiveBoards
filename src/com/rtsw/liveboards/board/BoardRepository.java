package com.rtsw.liveboards.board;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.rtsw.liveboards.model.Board;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {

    private static Logger L = LogManager.getLogger(BoardRepository.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    LocalMap<String, Board> boards;

    public BoardRepository(Vertx vertx) {
        boards = vertx.sharedData().getLocalMap("boards");
        L.debug(String.format("board repository contains %d board(s)", boards.size()));
    }

    public int create(Board board) throws Exception {
        if (boards.containsKey(board.getName())) {
            throw new Exception(String.format("board '%s' already exists"));
        }
        boards.put(board.getName(), board);
        L.info(String.format("added board '%s'", board.getName()));
        return (1);
    }

    public int create(JsonNode input) throws Exception {
        JsonNode schema = objectMapper.readTree(BoardRepository.class.getResource("/schema/board.json"));
        L.info("loaded schema for 'board'");
        JsonValidator validator = factory.getValidator();
        ProcessingReport processingReport = validator.validate(schema, input);
        if (processingReport.isSuccess()) {
            Board board = objectMapper.treeToValue(input, Board.class);
            L.info(String.format("loaded board from input '%s'", input.toString()));
            return (create(board));
        } else {

            System.err.println(processingReport.toString());

            throw new Exception(String.format("input from '%s' does not pass validation", input.toString()));
        }
    }

    public Board get(String name) {
        return (boards.get(name));
    }

    public List<Board> get() {
        return (new ArrayList<>(boards.values()));
    }

    public List<String> getNames() {
        return (new ArrayList<>(boards.keySet()));
    }

    public List<Board> findByTable(String tableName) {
        List<Board> result = new ArrayList<>();
        for (Board board : boards.values()) {
            if (board.getLabel().getTable().equals(tableName)) {
                result.add(board);
            }
        }
        return (result);
    }

    public int delete(String boardName) throws Exception {
        Board board = boards.get(boardName);
        if (board == null) {
            throw new Exception(String.format("board '%s' not found", boardName));
        }
        boards.remove(boardName);
        return (1);
    }

}
