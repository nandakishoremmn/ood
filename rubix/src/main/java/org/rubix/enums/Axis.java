package org.rubix.enums;

public enum Axis {
    Y,
    X_MINUS,
    Z,
    X,
    Z_MINUS,
    Y_MINUS;

    public Axis next() {
        switch (this) {
            case Y:
                return Z;
            case X_MINUS:
                return Y_MINUS;
            case Z:
                return X;
            case X:
                return Y;
            case Z_MINUS:
                return X_MINUS;
            default:
                return Z_MINUS;
        }
    }

    public Axis opposite() {
        switch (this) {
            case Y:
                return Y_MINUS;
            case X_MINUS:
                return X;
            case Z:
                return Z_MINUS;
            case X:
                return X_MINUS;
            case Z_MINUS:
                return Z;
            default:
                return Y;
        }
    }
}
