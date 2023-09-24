package com.rtsw.liveboards.test;

import com.rtsw.liveboards.board.aggregation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Aggregation {

    private static List<Integer> INTEGERS = List.of(3, 5, 1, 4, 9, 10, 8, 7, 2, 6);

    private static List<Double> DOUBLES = List.of(3.1, 5.9, 1.0, 4.2, 9.7, 10.1, 8.8, 7.9, 2.1, 6.3);

    private static List<String> STRINGS = List.of("Cat", "Dog", "Turtle", "Fish", "Cat", "Snake", "Cow", "Horse", "Cat", "Dog");

    @Test
    public void average_integers() {
        AverageAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new AverageAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(5.5));
    }

    @Test
    public void average_doubles() {
        AverageAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new AverageAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        DecimalFormat format = new DecimalFormat("#.##", symbols);
        Assertions.assertTrue(format.format(aggregation.value()).equals("5.91"));
    }

    @Test
    public void count_integers() {
        CountAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new CountAggregation();
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(10));
    }

    @Test
    public void count_doubles() {
        CountAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new CountAggregation();
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(10));
    }

    @Test
    public void distinct_integers() {
        DistinctAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new DistinctAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(10));
    }

    @Test
    public void distinct_doubles() {
        DistinctAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new DistinctAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(10));
    }

    @Test
    public void first_integers() {
        FirstAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new FirstAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(3));
    }

    @Test
    public void first_doubles() {
        FirstAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new FirstAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(3.1));
    }

    @Test
    public void last_integers() {
        LastAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new LastAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(6));
    }

    @Test
    public void last_doubles() {
        LastAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new LastAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(6.3));
    }

    @Test
    public void maximum_integers() {
        MaximumAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new MaximumAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(10));
    }

    @Test
    public void maximum_doubles() {
        MaximumAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new MaximumAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(10.1));
    }

    @Test
    public void minimum_integers() {
        MinimumAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new MinimumAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(1));
    }

    @Test
    public void minimum_doubles() {
        MinimumAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new MinimumAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(1.0));
    }

    @Test
    public void sum_integers() {
        SumAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new SumAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(55));
    }

    @Test
    public void sum_doubles() {
        SumAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new SumAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        DecimalFormat format = new DecimalFormat("#.#", symbols);
        Assertions.assertTrue(format.format(aggregation.value()).equals("59.1"));
    }

    @Test
    public void range_integers() {
        RangeAggregation aggregation = null;
        for (Integer i : INTEGERS) {
            if (aggregation == null) {
                aggregation = new RangeAggregation(i);
            } else {
                aggregation.aggregate(i);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(9));
    }

    @Test
    public void range_doubles() {
        RangeAggregation aggregation = null;
        for (Double d : DOUBLES) {
            if (aggregation == null) {
                aggregation = new RangeAggregation(d);
            } else {
                aggregation.aggregate(d);
            }
        }
        Assertions.assertTrue(aggregation.value().equals(9.1));
    }

    @Test
    public void mode_strings() {
        ModeAggregation aggregation = null;
        for (String s : STRINGS) {
            if (aggregation == null) {
                aggregation = new ModeAggregation(s);
            } else {
                aggregation.aggregate(s);
            }
        }
        Assertions.assertTrue(aggregation.value().equals("Cat"));
    }

}
