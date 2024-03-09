package com.rtsw.liveboards.openapi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtsw.liveboards.model.Column;
import com.rtsw.liveboards.model.ModelRepository;
import com.rtsw.liveboards.model.Table;

import java.text.SimpleDateFormat;
import java.util.*;

public class API310Generator {

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<String> boards;

    private List<Table> tables;

    public API310Generator(List<String> boards, List<Table> tables) {
        this.boards = boards;
        this.tables = tables;
    }

    public Map generate() throws Exception {

        JsonNode boardSchema = objectMapper.readTree(ModelRepository.class.getResource("/schema/board.json"));
        JsonNode tableSchema = objectMapper.readTree(ModelRepository.class.getResource("/schema/table.json"));
        JsonNode errorResponseSchema = objectMapper.readTree(ModelRepository.class.getResource("/schema/response/error.json"));
        JsonNode stringListResponseSchema = objectMapper.readTree(ModelRepository.class.getResource("/schema/response/string_list.json"));
        JsonNode affectedResponseSchema = objectMapper.readTree(ModelRepository.class.getResource("/schema/response/affected.json"));
        JsonNode healthCheckResponseSchema = objectMapper.readTree(ModelRepository.class.getResource("/schema/response/health_check.json"));

        Map<String, Object> root = new HashMap<>();
        root.put("openapi", "3.1.0");
        root.put("info", Map.of(
                "title", "LiveBoards",
                "description", "LiveBoards Autogenerated API Definition",
                "version", 0.1,
                "license", Map.of("name", "MIT License", "url", "https://github.com/rtswstudio/LiveBoards/blob/master/LICENSE.md"),
                "contact", Map.of("name", "RT Software Studio", "email", "rt.sw.studio@gmail.com")));
        root.put("servers", Set.of(Map.of("url", "http://localhost:8888/api/v1", "description", "Local Development Environment")));
        Map<String, Object> paths = new HashMap<>();
        paths.put("/api/v1/health", Map.of(
                "get", Map.of(
                        "description", "Check server health and return key metrics",
                        "responses", Map.of(
                                "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                "200", Map.of(
                                        "description", "Health check returned successfully",
                                        "content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/HealthCheck"))))))));
        paths.put("/api/v1/boards", Map.of(
                "get", Map.of(
                        "description", "Return a list of all board names",
                        "responses", Map.of(
                                "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                "200", Map.of(
                                        "description", "List of board names returned successfully",
                                        "content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/StringList")))))),
                "post", Map.of(
                        "description", "Create new board",
                        "requestBody", Map.of("content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/Board")))),
                        "responses", Map.of(
                                "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                "200", Map.of(
                                        "description", "New board created successfully",
                                        "content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/Affected"))))))));
        paths.put("/api/v1/tables", Map.of(
                "get", Map.of(
                        "description", "Return a list of all table names",
                        "responses", Map.of(
                                "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                "200", Map.of(
                                        "description", "List of table names returned successfully",
                                        "content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/StringList")))))),
                "post", Map.of(
                        "description", "Create a new table",
                        "requestBody", Map.of("content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/Table")))),
                        "responses", Map.of(
                                "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                "200", Map.of(
                                        "description", "New table created successfully",
                                        "content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/Affected"))))))));
        for (String board : boards) {
            paths.put(String.format("/api/v1//boards/%s", board), Map.of(
                    "get", Map.of(
                            "description", String.format("Return HTML code to visualize board '%s' in a browser", board),
                            "responses", Map.of(
                                    "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                    "200", Map.of(
                                            "description", "Board rendered successfully",
                                            "content", Map.of("text/html", Map.of("schema", Map.of("type", "string")))),
                                    "404", Map.of(
                                            "description", "Board not found",
                                            "content", Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))))),
                    "delete", Map.of(
                            "description", String.format("Delete board '%s'", board),
                            "responses", Map.of(
                                    "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                    "200", Map.of(
                                            "description", "Board deleted successfully",
                                            "content", Map.of("application/json", Map.of("schema", Map.of("$ref", "#/components/schemas/Affected")))),
                                    "404", Map.of(
                                            "description", "Board not found",
                                            "content", Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error"))))))));
        }
        for (Table table : tables) {
            paths.put(String.format("/api/v1/tables/%s", table.getName()), Map.of(
                    "delete", Map.of(
                            "description", String.format("Delete table '%s'", table.getName()),
                            "responses", Map.of(
                                    "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                    "200", Map.of(
                                            "description", "Table deleted successfully",
                                            "content", Map.of("application/json", Map.of("schema", affectedResponseSchema))),
                                    "404", Map.of(
                                            "description", "Table not found",
                                            "content", Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error"))))))));

            Map<String, Object> properties = new HashMap<>();
            Set<String> required = new HashSet<>();
            for (Column column : table.getColumns()) {
                properties.put(column.getName(), Map.of("type", type(column.getType())));
                required.add(column.getName());
            }

            paths.put(String.format("/tables/%s/rows", table.getName()), Map.of(
                    "post", Map.of(
                            "description", String.format("Create new row and add it to table '%s'", table.getName()),
                            "requestBody", Map.of("content", Map.of("application/json", Map.of("schema", Map.of("type", "object", "properties", properties, "required", required)))),
                            "responses", Map.of(
                                    "500", Map.of("description", "A server error occurred", "content",  Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error")))),
                                    "200", Map.of(
                                            "description", "Row added successfully",
                                            "content", Map.of("application/json", Map.of("schema", affectedResponseSchema))),
                                    "404", Map.of(
                                            "description", "Table not found",
                                            "content", Map.of("text/plain", Map.of("schema", Map.of("$ref", "#/components/schemas/Error"))))))));
        }
        root.put("paths", paths);
        root.put("components", Map.of("schemas", Map.of(
                "Error", errorResponseSchema,
                "StringList", stringListResponseSchema,
                "Affected", affectedResponseSchema,
                "Board", boardSchema,
                "Table", tableSchema,
                "HealthCheck", healthCheckResponseSchema)));
        return (root);
    }

    private String type(String s) {
        switch (s) {
            case "string": return ("string");
            case "integer": return ("integer");
            case "decimal": return ("number");
            case "boolean": return ("boolean");
            case "timestamp": return ("string");
            default: return ("string");
        }
    }

}
