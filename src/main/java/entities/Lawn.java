package entities;

import enums.Direction;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Lawn {
    public int height;
    public int width;
    public Square[][] squares;
    public List<Mower> Mowers;
    public List<Crater> Craters;

    public Lawn() {
        this.Mowers = new ArrayList<Mower>();
        this.Craters = new ArrayList<Crater>();
    }

    public Lawn(int height, int width) {
        height = height;
        width = width;
        squares = new Square[height][width];
    }

    private void BuildSquares() {
      //refactor to enum type in square
        for (Mower mower: Mowers) {
            Point startLocation = mower.startLocation;
            squares[startLocation.x][startLocation.y] = new Empty(startLocation);
        }

        for (Crater crater: Craters) {
            Point location = crater.coordinate;
            squares[location.x][location.y] = new Crater(location);
        }

        for (int i = 0; i < height ; i++) {

            for (int j = 0; j < width ; j++) {

                if (squares[i][j]  == null) {
                    squares[i][j] = new Grass(new Point(i,j));
                }

            }


        }
    }

    public void UpdateMowerCrashStatus(int MowerID) {
        Mowers.get(MowerID).UpdateHasCrashed();
    }

    public void MowerTakeAction(int MowerID) {
        Mowers.get(MowerID).Move(Direction.north, new Point(0,0));
    }


    public void GetStateInformationOfSquares() {


    }

    public int Area() {
        return this.height * this.width;
    }

    public void MarkGrassAsCut(int MowerID) {
        Mowers.get(MowerID).IncrementCutGrassSquare();
    }


}