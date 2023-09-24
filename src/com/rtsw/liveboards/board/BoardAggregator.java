package com.rtsw.liveboards.board;

import com.rtsw.liveboards.board.aggregation.*;
import com.rtsw.liveboards.model.Board;
import com.rtsw.liveboards.model.ModelRepository;
import com.rtsw.liveboards.model.ModelUtils;
import com.rtsw.liveboards.model.Row;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

public class BoardAggregator {

    private static Logger L = LogManager.getLogger(BoardAggregator.class);

    private Map<String, Map<String, Aggregation>> view;

    private Vertx vertx;

    private Board board;

    public BoardAggregator(Vertx vertx, Board board) {
        this.vertx = vertx;
        this.board = board;
        view = new HashMap<>();
    }

    public void aggregate(List<Row> rows) {
        long start = System.currentTimeMillis();

        ModelRepository modelRepository = new ModelRepository(vertx);
        for (Row row : rows) {
            int labelColumnIndex = ModelUtils.columnIndex(modelRepository.getTable(board.getLabel().getTable()), board.getLabel().getColumn());
            String label = row.getValues().get(labelColumnIndex).toString();
            for (int columnIndex = 0; columnIndex < board.getValues().size(); columnIndex++) {
                String category = board.getValues().get(columnIndex).getCategory();
                int valueColumnIndex = ModelUtils.columnIndex(modelRepository.getTable(board.getValues().get(columnIndex).getTable()), board.getValues().get(columnIndex).getColumn());
                Object value = row.getValues().get(valueColumnIndex);
                switch (board.getValues().get(columnIndex).getAggregate()) {
                    case "count": {
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                CountAggregation aggregation = (CountAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new CountAggregation());
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new CountAggregation());
                            view.put(label, map);
                        }
                        break;
                    }
                    case "distinct": {
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                DistinctAggregation aggregation = (DistinctAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new DistinctAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new DistinctAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "sum": {
                        if (!SumAggregation.ALLOWED_CLASSES.contains(value.getClass())) {
                            L.warn(String.format("sum aggregation not allowed for class %s", value.getClass().getName()));
                            break;
                        }
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                SumAggregation aggregation = (SumAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new SumAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new SumAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "minimum": {
                        if (!MinimumAggregation.ALLOWED_CLASSES.contains(value.getClass())) {
                            L.warn(String.format("minimum aggregation not allowed for class %s", value.getClass().getName()));
                            break;
                        }
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                MinimumAggregation aggregation = (MinimumAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new MinimumAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new MinimumAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "maximum": {
                        if (!MaximumAggregation.ALLOWED_CLASSES.contains(value.getClass())) {
                            L.warn(String.format("maximum aggregation not allowed for class %s", value.getClass().getName()));
                            break;
                        }
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                MaximumAggregation aggregation = (MaximumAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new MaximumAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new MaximumAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "average": {
                        if (!AverageAggregation.ALLOWED_CLASSES.contains(value.getClass())) {
                            L.warn(String.format("average aggregation not allowed for class %s", value.getClass().getName()));
                            break;
                        }
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                AverageAggregation aggregation = (AverageAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new AverageAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new AverageAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "first": {
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                FirstAggregation aggregation = (FirstAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new FirstAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new FirstAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "last": {
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                LastAggregation aggregation = (LastAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new LastAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new LastAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "range": {
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                RangeAggregation aggregation = (RangeAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new RangeAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new RangeAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                    case "mode": {
                        if (view.containsKey(label)) {
                            Map<String, Aggregation> map = view.get(label);
                            if (map.containsKey(category)) {
                                ModeAggregation aggregation = (ModeAggregation) view.get(label).get(category);
                                aggregation.aggregate(value);
                                map.put(category, aggregation);
                            } else {
                                map.put(category, new ModeAggregation(value));
                            }
                        } else {
                            Map<String, Aggregation> map = new HashMap();
                            map.put(category, new ModeAggregation(value));
                            view.put(label, map);
                        }
                        break;
                    }
                }
            }
        }

        L.debug(String.format("aggregated view contains %d label(s)", view.size()));

        long end = System.currentTimeMillis();
        L.debug(String.format("aggregation completed in %d milliseconds", (end - start)));
    }

    public String[] labels() {
        List<String> list = new ArrayList<>(view.keySet());
        Collections.sort(list);
        return (list.toArray(String[]::new));
    }

    public Object[] values(String category) {
        Object[] result = new Object[view.keySet().size()];
        int index = 0;
        for (String label : labels()) {
            Map<String, Aggregation> values = view.get(label);
            Object object = values.get(category).value();
            result[index] = object;
            index++;
        }
        return (result);
    }

    public String[] categories() {
        Set<String> result = new HashSet<>();
        for (String label : labels()) {
            for (String category : view.get(label).keySet()) {
                result.add(category);
            }
        }
        List<String> list = new ArrayList<>(result);
        Collections.sort(list);
        return (list.toArray(String[]::new));
    }

    public Map<String, Object[]> values() {
        Map<String, Object[]> result = new HashMap<>();
        for (String category : categories()) {
            result.put(category, values(category));
        }
        return (result);
    }

}
