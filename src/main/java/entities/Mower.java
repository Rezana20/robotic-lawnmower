package entities;

import java.awt.Point;
import enums.Direction;
import enums.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor
public class Mower {
    public Point location;
    public boolean hasCrashed = false;
    public Direction currentDirection;
    public int countCutGrass = 0;
    public Point startLocation;
    public List<Direction> previousDirections;

    public Strategy strategy;

    public Mower(Point startLocation, Direction direction, int strategy) {
        this.startLocation = startLocation;
        this.location = startLocation;
        this.currentDirection = direction;
        if(strategy == 0 ) {
            this.strategy = Strategy.RANDOM;
        }
        else {
            this.strategy = Strategy.CUSTOM;
        }


    }


    public void Move(Direction direction, Point location) {
        AddToPreviousDirections();
        UpdateCurrentDirection(direction);
        this.location = location;
    }

    public void Steer() {

    }

    public void Scan() {

    }

    public void Pass() {

    }

    public void Cut() {

    }

    public void Display() {
        System.out.println("Cut: "+countCutGrass);
    }



    public void UpdateHasCrashed() {
        hasCrashed = true;
    }

    private void AddToPreviousDirections(){
        this.previousDirections.add(currentDirection);
    }

    private void UpdateCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void IncrementCutGrassSquare() {
     this.countCutGrass++;
    }
}