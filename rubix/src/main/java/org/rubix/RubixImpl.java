package org.rubix;

import lombok.Getter;
import org.rubix.data.Layer;
import org.rubix.data.Location;
import org.rubix.display.RubixGridDisplay;
import org.rubix.enums.Axis;
import org.rubix.iface.Rubix;
import org.rubix.iface.RubixDisplay;

import java.util.*;
import java.util.stream.IntStream;

public class RubixImpl implements Rubix {
    @Getter
    private final Integer size;

    private final Map<Location, Cell> cells = new HashMap<>();
    private Map<Layer, Set<Cell>> layers = new HashMap<>();

    private final RubixDisplay display = new RubixGridDisplay();
    private final LayerGenerator layerGenerator = new LayerGenerator();
    private final RelationGenerator relationGenerator = new RelationGenerator();
//    private final RubixDisplay display = new RubixLineDisplay();

    public RubixImpl(Integer size) {
        this.size = size;
        createCells();
        relationGenerator.generateRelations(cells, size);
        layers = layerGenerator.generateLayers(cells, size);
    }

    private void createCells() {
        IntStream.range(0, this.size).forEach(i -> {
            IntStream.range(0, this.size).forEach(j -> {
                Arrays.stream(Axis.values()).forEach(axis -> {
                    Cell cell = new Cell(new Location(axis, i, j), axis.ordinal() + 1);
                    cells.put(cell.getLocation(), cell);
                });
            });
        });
    }

    public void rotateLayer(Axis axis, Integer layer, Integer angle) {
        List<Cell> cells = selectCellsInLayer(axis, layer - 1);
        angle = (angle + 4) % 4;
        IntStream.range(0, angle).forEach(i -> {
            cells.forEach(cell -> cell.rotate(axis));
            cells.forEach(Cell::commit);
        });
    }

    private List<Cell> selectCellsInLayer(Axis axis, Integer layer) {
        return new ArrayList<>(layers.get(new Layer(layer, axis)));
    }

    public void display() {
        display.display(cells, size);
    }

    public void rotateCube(Axis axis, Integer angle) {
        IntStream.range(0, this.size).forEach(i -> rotateLayer(axis, i+1, angle));
    }

}
