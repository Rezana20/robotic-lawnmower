package entities;

import java.awt.Point;

public class Square {

    public Point coordinate;
    public String description = "square";

    public Square(Point coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return description;
    }
}