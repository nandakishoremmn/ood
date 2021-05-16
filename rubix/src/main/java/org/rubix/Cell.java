package org.rubix;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.rubix.data.Location;
import org.rubix.enums.Axis;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class Cell {
    Location location;
    Integer color;
    Integer tempColor;
    @EqualsAndHashCode.Exclude
    Map<Axis, Cell> relations;

    public Cell(Location location, Integer color) {
        this.location = location;
        this.color = color;
        this.tempColor = this.color;
        this.relations = new HashMap<>();
    }

    public void rotate(Axis axis) {
        this.tempColor = relations.get(axis).color;
    }

    public void commit() {
        this.color = this.tempColor;
    }

    public void setRelations(Map<Axis, Cell> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "location=" + location +
                ", color=" + color +
                ", tempColor=" + tempColor +
                '}';
    }
}
