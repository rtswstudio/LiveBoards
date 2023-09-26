package com.rtsw.liveboards.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.rtsw.liveboards.configuration.ConfigurationRepository;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.LocalMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ModelRepository {

    private static Logger L = LogManager.getLogger(ModelRepository.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    private LocalMap<String, Table> tables;

    private LocalMap<String, List<Row>> data;

    public ModelRepository(Vertx vertx) {
        tables = vertx.sharedData().getLocalMap("tables");
        data = vertx.sharedData().getLocalMap("data");
        L.debug(String.format("model repository contains %d table(s)", tables.size()));
    }

    public int createTable(Table table) throws Exception {
        if (tables.containsKey(table.getName())) {
            throw new Exception(String.format("table '%s' already exists"));
        }
        tables.put(table.getName(), table);
        L.info(String.format("added table '%s'", table.getName()));
        return (1);
    }

    public int createTable(JsonNode input) throws Exception {
        JsonNode schema = objectMapper.readTree(ModelRepository.class.getResource("/schema/table.json"));
        L.info("loaded schema for 'table'");
        JsonValidator validator = factory.getValidator();
        ProcessingReport processingReport = validator.validate(schema, input);
        if (processingReport.isSuccess()) {
            Table table = objectMapper.treeToValue(input, Table.class);
            L.info(String.format("loaded table from input '%s'", input.toString()));
            return (createTable(table));
        } else {
            throw new Exception(String.format("input from '%s' does not pass validation", input.toString()));
        }
    }

    public int deleteTable(String tableName) throws Exception {
        Table table = tables.get(tableName);
        if (table == null) {
            throw new Exception(String.format("table '%s' not found", tableName));
        }
        tables.remove(tableName);
        return (1);
    }

    public List<Table> getTables() {
        return (new ArrayList<>(tables.values()));
    }

    public List<String> getTableNames() {
        return (new ArrayList<>(tables.keySet()));
    }

    public Table getTable(String tableName) {
        return (tables.get(tableName));
    }

    public int createRow(String tableName, JsonObject jsonObject) throws Exception {
        Table table = tables.get(tableName);
        if (table == null) {
            throw new Exception(String.format("table '%s' not found", tableName));
        }
        List<Object> values = new ArrayList<>();
        for (Column column : table.getColumns()) {
            try {
                switch (column.getType()) {
                    case "string": values.add(jsonObject.getString(column.getName())); break;
                    case "integer": values.add(jsonObject.getInteger(column.getName())); break;
                    case "decimal": values.add(jsonObject.getDouble(column.getName())); break;
                    case "boolean": values.add(jsonObject.getBoolean(column.getName())); break;
                    case "timestamp": values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString(column.getName()))); break;
                }
            } catch (Exception e) {
                throw new Exception(String.format("cannot parse value '%s' as '%s' to column '%s'", null, column.getType(), column.getName()));
            }
        }
        Row row = new Row(values);
        if (!data.containsKey(tableName)) {
            List<Row> rows = new ArrayList<>();
            synchronized (rows) {
                rows.add(row);
                if (table.getCapacity() != -1 && rows.size() >= table.getCapacity()) {
                    rows.remove(0);
                }
            }
            data.put(tableName, rows);
        } else {
            List<Row> rows = data.get(tableName);
            synchronized (rows) {
                rows.add(row);
                if (table.getCapacity() != -1 && rows.size() >= table.getCapacity()) {
                    rows.remove(0);
                }
            }
            data.put(tableName, rows);
        }
        L.info(String.format("added row to table '%s'", tableName));
        return (1);
    }

    public List<Row> listRows(String tableName) {
        return (data.get(tableName));
    }

}
