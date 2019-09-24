package enums;

import java.util.Random;

public enum Direction {
    north("north"),
    northeast("northeast"),
    east("east"),
    southeast("southeast"),
    south("south"),
    southwest("southwest"),
    west("west"),
    northwest("northwest");

    private static Random rnd = new Random();
    public String code;

    Direction(String code) {
        this.code = code;
    }

    static public Direction RandomDirection() {
        return Direction.values()[rnd.nextInt(8)];
    }
}
