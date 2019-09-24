package entities;

import java.awt.*;

public class Crater extends Square {
    public String type;

    public Crater(Point Coordinate) {
        super(Coordinate);
        type = "crater";
    }
}