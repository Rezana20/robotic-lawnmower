package entities;

import java.awt.Point;

public class Empty extends Square {

    public Empty(Point coordinate) {
        super(coordinate);
        description = "empty";
    }
}