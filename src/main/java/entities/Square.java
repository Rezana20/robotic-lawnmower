package entities;

import java.awt.Point;

public class Square {

    public Point coordinate;
    public String type;

    public Square(Point Coordinate){
        coordinate = Coordinate;
        type = "Square";
    }


    public void changeType(String newType) {
        this.type = newType;
    }

    public Square() {

    }

    @Override
    public String toString() {
        return "Square{" +
                "coordinate=" + coordinate +
                ", type='" + type + '\'' +
                '}';
    }
}