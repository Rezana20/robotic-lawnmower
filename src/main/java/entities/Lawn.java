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
    public List<Mower> mowers;
    public List<Crater> craters;
    public int totalGrassSquares = 0;
    public boolean allGrassCut;


    public Lawn(int height, int width, List<Mower> mowers, List<Crater> craters) {
        this.height = height;
        this.width = width;
        this.squares = new Square[width][height];
        this.mowers = mowers;
        this.craters = craters;
        BuildSquares();
    }

    private void BuildSquares() {
        //refactor to enum type in square
        for (Mower mower : mowers) {
            Point coordinate = mower.startCoordinate;
            squares[coordinate.x][coordinate.y] = new Empty(coordinate);
        }

        for (Crater crater : craters) {
            Point coordinate = crater.coordinate;
            squares[coordinate.x][coordinate.y] = new Crater(coordinate);
        }

        for (int i = 0; i < width; ++i) {

            for (int j = 0; j < height; ++j) {

                if (squares[i][j] == null) {
                    Point newPoint = new Point(i,j);
                    Grass newGrass = new Grass(newPoint);
                    squares[i][j] = newGrass;
                    totalGrassSquares++;
                }

            }


        }
    }

    public void UpdateMowerCrashStatus(int MowerID) {
        mowers.get(MowerID).UpdateHasCrashed();
    }

    public void MowerTakeAction(int MowerID) {
        mowers.get(MowerID).Move(Direction.north, new Point(0, 0));
    }


    public void GetStateInformationOfSquares() {


    }

    public int Area() {
        return this.height * this.width;
    }

    public void MarkGrassAsCut(int MowerID) {
        mowers.get(MowerID).IncrementCutGrassSquare();
    }

    public boolean CountAllGrassCut() {
        int currentCutSquares = 0;

        for(Mower mower: mowers){
            currentCutSquares += mower.countCutGrass;
        }

        return currentCutSquares == totalGrassSquares;
    }


}