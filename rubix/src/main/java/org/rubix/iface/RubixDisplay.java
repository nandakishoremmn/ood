package org.rubix.iface;

import org.rubix.Cell;
import org.rubix.data.Location;

import java.util.Map;

public interface RubixDisplay {
    void display(Map<Location, Cell> cells, Integer size);
}
