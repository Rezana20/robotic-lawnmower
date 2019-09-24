package entities;

import java.awt.*;

public class Empty extends Square {
    public String type;

    public Empty(Point Coordinate) {
        super(Coordinate);
        type = "empty";
    }
}