package org.rubix;

import org.rubix.enums.Axis;
import org.rubix.iface.Rubix;

public class App {
    public static void main(String[] args) {
        Rubix rubix = new RubixImpl(3);
        rubix.rotateCube(Axis.Y, 2);

        rubix.display();
        rubix.rotateLayer(Axis.X, 2, 1);
        rubix.rotateCube(Axis.Y, 2);

        rubix.rotateLayer(Axis.X, 2, 1);
        rubix.display();
        System.out.println("");
    }
}
