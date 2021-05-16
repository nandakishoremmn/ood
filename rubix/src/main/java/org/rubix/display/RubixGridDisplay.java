package org.rubix.display;

import org.rubix.Cell;
import org.rubix.data.Location;
import org.rubix.enums.Axis;
import org.rubix.iface.RubixDisplay;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

public class RubixGridDisplay implements RubixDisplay {
    @Override
    public void display(Map<Location, Cell> cells, Integer size) {
        int[][] grid = new int[size * 3][size * 4];
        Arrays.stream(Axis.values()).forEach(axis -> {
            IntStream.range(0, size).forEach(i -> {
                IntStream.range(0, size).forEach(j -> {
                    int p, q;
                    int x, y;
                    switch (axis) {
                        case Y:
                            p = i;
                            q = j;
                            y = 3;
                            x = 0;
                            break;
                        case X_MINUS:
                            p = size - i - 1;
                            q = j;
                            y = 0;
                            x = 3;
                            break;
                        case Z:
                            p = j;
                            q = size - i - 1;
                            y = 3;
                            x = 3;
                            break;
                        case X:
                            p = size - i - 1;
                            q = size - j - 1;
                            y = 6;
                            x = 3;
                            break;
                        case Z_MINUS:
                            p = size - j - 1;
                            q = size - i - 1;
                            y = 9;
                            x = 3;
                            break;
                        default:
                            p = size - i - 1;
                            q = j;
                            y = 3;
                            x = 6;
                            break;
                    }
                    grid[i + x][j + y] = cells.get(new Location(axis, p, q)).getColor();
                });
            });
        });
        Arrays.stream(grid).forEach(row -> {
            Arrays.stream(row).forEach(e -> {
                System.out.print(e == 0 ? " " : e);
            });
            System.out.println("");
        });
        System.out.println("");
    }
}
