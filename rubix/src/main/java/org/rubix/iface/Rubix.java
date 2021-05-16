package org.rubix.iface;

import org.rubix.enums.Axis;

public interface Rubix {
    void rotateLayer(Axis axis, Integer layer, Integer angle);
    void display();
    void rotateCube(Axis axis, Integer angle);
}
