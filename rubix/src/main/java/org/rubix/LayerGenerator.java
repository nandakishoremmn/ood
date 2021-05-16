package org.rubix;

import org.rubix.data.Layer;
import org.rubix.data.Location;
import org.rubix.enums.Axis;

import java.util.*;
import java.util.stream.IntStream;

public class LayerGenerator {

    Map<Layer, Set<Cell>> generateLayers(Map<Location, Cell> cells, Integer size) {
        Map<Layer, Set<Cell>> layers = new HashMap<>();
        Arrays.asList(Axis.X, Axis.Y, Axis.Z).forEach(axis -> {
            IntStream.range(0, size).forEach(index -> {
                Layer layer = new Layer(index, axis);
                Set<Cell> cellSet = new HashSet<>();
                IntStream.range(0, size).forEach(i -> {
                    cellSet.add(cells.get(new Location(axis.next().opposite(), i, index)));
                    cellSet.add(cells.get(new Location(axis.next().next().opposite(), index, i)));
                    cellSet.add(cells.get(new Location(axis.next(),i, index)));
                    cellSet.add(cells.get(new Location(axis.next().next(), index, i)));
                });
                if (index == 0) {
                    IntStream.range(0, size).forEach(i -> {
                        IntStream.range(0, size).forEach(j -> {
                            cellSet.add(cells.get(new Location(axis.opposite(), i, j)));
                        });
                    });
                }
                if (index == size - 1) {
                    IntStream.range(0, size).forEach(i -> {
                        IntStream.range(0, size).forEach(j -> {
                            cellSet.add(cells.get(new Location(axis, i, j)));
                        });
                    });
                }
                layers.put(layer, cellSet);
            });
        });
        return layers;
    }
}
