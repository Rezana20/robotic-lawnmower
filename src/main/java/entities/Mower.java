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
    public Point coordinate;
    public boolean hasCrashed = false;
    public Direction currentDirection;
    public int countCutGrass = 0;
    public Point startCoordinate;
    public List<Direction> previousDirections;

    public Strategy strategy;

    public Mower(Point startCoordinate, Direction direction, int strategy) {
        this.startCoordinate = startCoordinate;
        this.coordinate = startCoordinate;
        this.currentDirection = direction;

        if (strategy == 0) {
            this.strategy = Strategy.RANDOM;
        } else {
            this.strategy = Strategy.CUSTOM;
        }


    }


    public void Move(Direction direction, Point newCoordinate) {
        AddToPreviousDirections();
        UpdateCurrentDirection(direction);
        this.coordinate = newCoordinate;
    }

    public void Steer(Direction direction) {
        currentDirection = direction;
    }

    //TODO identify mower cells  - right now they will be empty
    public void Scan(Square[][] squares) {
        String neighbors = "";

        int[] x = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] y = {1, 1, 0, -1, -1, -1, 0, 1};

        for (int k = 0; k < 8; k++) {
            if (isValid(coordinate.x + x[k], coordinate.y + y[k], squares.length)) {
                neighbors += squares[coordinate.x + x[k]][coordinate.y + y[k]].description + ", ";
            } else {
                neighbors += "fence, ";
            }
        }

        System.out.println(neighbors.substring(0, neighbors.length() - 2));

    }


    public void Pass() {

    }

    public void Cut() {

    }

    public void Display() {
        System.out.println("Cut: " + countCutGrass);
    }


    public void UpdateHasCrashed() {
        hasCrashed = true;
    }

    private void AddToPreviousDirections() {
        this.previousDirections.add(currentDirection);
    }

    private void UpdateCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void IncrementCutGrassSquare() {
        this.countCutGrass++;
    }

    private boolean isValid(int x, int y, int len) {
        if (x < 0 || y < 0 || x >= len || y >= len)
            return false;
        return true;
    }
}