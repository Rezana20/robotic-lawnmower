package entities;

import java.awt.Point;

import enums.Direction;
import enums.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Mower {

    public String mowerId;
    public Point coordinate;
    public boolean hasCrashed = false;
    public Direction currentDirection;
    public int countCutGrass = 1;
    public Point startCoordinate;
    public List<Direction> previousDirections;
    private String mowerStatus = "ok";

    public Mower(String mowerId, Point startCoordinate, Direction direction) {

        this.mowerId = mowerId;
        this.startCoordinate = startCoordinate;
        this.coordinate = startCoordinate;
        this.currentDirection = direction;

        this.previousDirections = new ArrayList<>();

    }

    public String Move(Point newCoordinate, String newSquareDetail) {

        this.coordinate = newCoordinate;

        if(newSquareDetail.equals("grass")) {
            IncrementCutGrassSquare();
        }

        if (validateMove(newSquareDetail)) {
            UpdateHasCrashed();
        }

        return mowerId + ",move \n" + mowerStatus;
    }

    public String Steer(Direction direction) {

        UpdateCurrentDirection(direction);
        return mowerId + ",steer," + direction.name()+"\nok";

    }

    public String Scan(Square[][] squares, List<Point> otherMowers) {
        String neighbors = "";

        int[] x = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] y = {1, 1, 0, -1, -1, -1, 0, 1};

        for (int k = 0; k < 8; k++) {

            if (isValid(coordinate.x + x[k], coordinate.y + y[k], squares.length, squares[0].length)) {

                Point currentPoint = new Point(coordinate.x + x[k], coordinate.y + y[k]);
                if (otherMowers.contains(currentPoint)) {
                    neighbors += "mower,";
                } else {
                    neighbors += squares[coordinate.x + x[k]][coordinate.y + y[k]].toString() + ",";
                }

            } else {
                neighbors += "fence,";
            }
        }

        return mowerId + ",scan \n" + neighbors.substring(0, neighbors.length() - 1);

    }

    public String Pass() {
        return mowerId + ",pass \nok";
    }

    public void IncrementCutGrassSquare() {
        this.countCutGrass++;
    }

    public void Cut() {    }

    public void Display() { System.out.println("Cut: " + countCutGrass); }

    public void UpdateHasCrashed() {
        hasCrashed = true;
        mowerStatus = "crash";
    }

    private boolean validateMove(String newSquareDetail) { return newSquareDetail == "fence" || newSquareDetail == "mower" || newSquareDetail == "crater"; }

    private void AddToPreviousDirections() { this.previousDirections.add(currentDirection); }

    private void UpdateCurrentDirection(Direction direction) {
        AddToPreviousDirections();
        this.currentDirection = direction;
    }

    public boolean isValid(int x, int y, int width, int height) { return x >= 0 && y >= 0 && x < width && y < height; }
}