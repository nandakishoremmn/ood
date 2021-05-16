package org.rubix.display;

import org.rubix.Cell;
import org.rubix.data.Location;
import org.rubix.enums.Axis;
import org.rubix.iface.RubixDisplay;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

public class RubixLineDisplay implements RubixDisplay {
    @Override
    public void display(Map<Location, Cell> cells, Integer size) {
        Arrays.stream(Axis.values()).forEach(axis -> {
            IntStream.range(0, size).forEach(i -> {
                IntStream.range(0, size).forEach(j -> {
                    int p, q;
                    switch (axis) {
                        case Y:
                            p = i;
                            q = j;
                            break;
                        case X_MINUS:
                            p = size - i - 1;
                            q = j;
                            break;
                        case Z:
                            p = j;
                            q = size - i - 1;
                            break;
                        case X:
                            p = size - i - 1;
                            q = size - j - 1;
                            break;
                        case Z_MINUS:
                            p = size - j - 1;
                            q = size - i - 1;
                            break;
                        default:
                            p = size - i - 1;
                            q = j;
                            break;
                    }
                    System.out.print(cells.get(new Location(axis, p, q)).getColor());
                });
            });
        });
        System.out.println("");
    }
}
