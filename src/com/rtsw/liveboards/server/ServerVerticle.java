package com.rtsw.liveboards.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtsw.liveboards.board.BoardAggregator;
import com.rtsw.liveboards.board.BoardFilter;
import com.rtsw.liveboards.board.BoardRepository;
import com.rtsw.liveboards.model.Board;
import com.rtsw.liveboards.model.ModelRepository;
import com.rtsw.liveboards.configuration.ConfigurationRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerVerticle extends AbstractVerticle {

    private static Logger L = LogManager.getLogger(ServerVerticle.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private SockJSBridgeOptions options = new SockJSBridgeOptions();

    @Override
    public void start() throws Exception {
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        // logging
        final Handler<RoutingContext> loggingHandler = ctx -> {
            L.debug(String.format("request uri '%s' from remote address '%s'", ctx.request().uri(), ctx.request().remoteAddress()));
            ctx.next();
        };
        router.route().handler(loggingHandler);

        // board repository
        BoardRepository boardRepository = new BoardRepository(vertx);

        // model repository
        ModelRepository modelRepository = new ModelRepository(vertx);

        // health check
        router.get("/api/:version/health").produces("application/json").handler(ctx -> {
            try {
                ctx.response().end(objectMapper.writeValueAsString(Map.of(
                        "version", "0.1",
                        "time", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()),
                        "processors", Map.of("available", Runtime.getRuntime().availableProcessors()),
                        "memory", Map.of("max", Runtime.getRuntime().maxMemory(), "total", Runtime.getRuntime().totalMemory(), "free", Runtime.getRuntime().freeMemory()),
                        "tables", Map.of("count", modelRepository.getTables().size()),
                        "boards", Map.of("count", boardRepository.get().size()))));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        // list boards
        router.get("/api/:version/boards").produces("application/json").handler(ctx -> {
            List<String> boards = boardRepository.getNames();
            try {
                ctx.response().end(objectMapper.writeValueAsString(boards));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        // get board
        HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create(vertx);
        router.get("/api/:version/boards/:name").handler(ctx -> {
            String name = ctx.pathParam("name");
            Board board = boardRepository.get(name);
            if (board == null) {
                ctx.response().setStatusCode(404).end(String.format("board '%s' not found", name));
                return;
            }
            BoardAggregator boardAggregator = new BoardAggregator(vertx, board);
            BoardFilter boardFilter = new BoardFilter(vertx, board);
            boardAggregator.aggregate(boardFilter.filter());
            Map<String, Object> params = new HashMap<>();
            params.put("title", board.getTitle());
            params.put("name", board.getName());
            params.put("labels", boardAggregator.labels());
            params.put("values", boardAggregator.values());
            try {
                params.put("options", objectMapper.writeValueAsString(board.getOptions()).replaceAll("\"", ""));
            } catch (Exception e) {
                // todo
            }
            engine.render(params, String.format("templates/%s.hbs", board.getType()), res -> {
                ctx.response().end(res.result());
            });
        });

        // delete board
        router.delete("/api/:version/boards/:name").produces("application/json").handler(ctx -> {
            String name = ctx.pathParam("nam");
            try {
                int affected = boardRepository.delete(name);
                Map<String, Object> response = Map.of("affected", affected, "time", System.currentTimeMillis());
                ctx.response().end(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        // create table
        router.post("/api/:version/tables").consumes("application/json").produces("application/json").handler(ctx -> {
            JsonObject body = ctx.getBodyAsJson();
            try {
                int affected = modelRepository.createTable(objectMapper.readTree(body.toString()));
                Map<String, Object> response = Map.of("affected", affected, "time", System.currentTimeMillis());
                ctx.response().end(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        // list tables
        router.get("/api/:version/tables").produces("application/json").handler(ctx -> {
            List<String> tables = modelRepository.getTableNames();
            try {
                ctx.response().end(objectMapper.writeValueAsString(tables));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        // delete table
        router.delete("/api/:version/tables/:name").produces("application/json").handler(ctx -> {
            String name = ctx.pathParam("nam");
            try {
                int affected = modelRepository.deleteTable(name);
                Map<String, Object> response = Map.of("affected", affected, "time", System.currentTimeMillis());
                ctx.response().end(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        // create row
        router.post("/api/:version/tables/:name/row").consumes("application/json").produces("application/json").handler(ctx -> {
            String name = ctx.pathParam("name");
            JsonObject body = ctx.getBodyAsJson();
            try {
                int affected = modelRepository.createRow(name, body);
                Map<String, Object> response = Map.of("affected", affected, "time", System.currentTimeMillis());
                for (Board board : boardRepository.findByTable(name)) {
                    BoardAggregator boardAggregator = new BoardAggregator(vertx, board);
                    BoardFilter boardFilter = new BoardFilter(vertx, board);
                    boardAggregator.aggregate(boardFilter.filter());
                    Map<String, Object> params = new HashMap<>();
                    params.put("labels", boardAggregator.labels());
                    params.put("values", boardAggregator.values());
                    try {
                        vertx.eventBus().publish(String.format("events.%s", board.getName()), objectMapper.writeValueAsString(params));
                    } catch (Exception e) {
                        L.warn(String.format("could not publish event to client: %s", e.getMessage()));
                        e.printStackTrace(System.err);
                    }
                }
                ctx.response().end(objectMapper.writeValueAsString(response));
            } catch (Exception e) {
                ctx.response().setStatusCode(500).end(e.getMessage());
                e.printStackTrace(System.err);
            }
        });

        options = new SockJSBridgeOptions();
        for (Board board : new BoardRepository(vertx).get()) {
            options.addOutboundPermitted(new PermittedOptions().setAddress("events.votes"));
            options.addOutboundPermitted(new PermittedOptions().setAddress("events.browsers"));
            options.addOutboundPermitted(new PermittedOptions().setAddress("events.measurements"));
        }
        router.mountSubRouter("/eventbus", SockJSHandler.create(vertx).bridge(options));

        int port = ConfigurationRepository.getRepository().get().getServer().getPort();
        String host = ConfigurationRepository.getRepository().get().getServer().getHost();
        httpServer.requestHandler(router).listen(port, host);
        L.info(String.format("listening on %s:%d", host, port));
    }

    @Override
    public void stop() throws Exception {
    }

}
