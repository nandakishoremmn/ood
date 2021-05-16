package org.rubix;

import org.rubix.data.Location;
import org.rubix.enums.Axis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RelationGenerator {
    void generateRelations(Map<Location, Cell> cells, Integer size) {
        IntStream.range(0, size).forEach(i -> {
            IntStream.range(0, size).forEach(j -> {
                Arrays.stream(Axis.values()).forEach(axis -> {
                    Axis tAxis = axis.toString().contains("MINUS") ? axis.opposite() : axis;
                    cells.get(new Location(axis, i, j)).setRelations(
                            new HashMap<Axis, Cell>() {
                                {
                                    put(tAxis, cells.get(new Location(axis, j, size - i - 1)));
                                    put(tAxis.next(), cells.get(new Location(axis.next().next(), j, i)));
                                    put(tAxis.next().next(), cells.get(new Location(axis.next().opposite(), j, size - i - 1)));
                                }
                            }
                    );
                });
            });
        });
    }
}
