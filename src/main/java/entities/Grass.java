package entities;
import java.awt.*;

public class Grass extends Square {

    public boolean IsCut;
    public String type;


    public Grass(Point Coordinate) {
        super(Coordinate);
        type = "grass";
    }

}