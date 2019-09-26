package entities;


import enums.Direction;
import enums.Strategy;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class Lawn {

    public int height;
    public int width;
    public Square[][] squares;
    public List<Mower> mowers;
    public List<Crater> craters;
    public List<Strategy> strategies;
    public int totalGrassSquares = 0;
    public boolean allGrassCut;
    public int crashedMowerCounter = 0;


    public Lawn(int height, int width, List<Mower> mowers, List<Crater> craters,List<Strategy> strategies) {
        this.height = height;
        this.width = width;
        this.squares = new Square[width][height];
        this.mowers = mowers;
        this.craters = craters;
        this.strategies = strategies;
        InitializeSquares();
    }

    private void InitializeSquares() {
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
                    Point newPoint = new Point(i, j);
                    Grass newGrass = new Grass(newPoint);
                    squares[i][j] = newGrass;
                    totalGrassSquares++;
                }

            }


        }
    }

    public void DisplayStateInformationOfSquares() {

        for (int j = height - 1; j >= 0; --j) {

            for (int i = 0; i < width; ++i) {

                Point currentPoint = new Point(i,j);
                boolean mowerPosition = false;

                for(Mower m: mowers) {
                    if(m.coordinate.equals(currentPoint)) {
                        mowerPosition = true;
                        break;
                    }
                }
                if (mowerPosition) {
                    System.out.print("mower" + "[" + i + "]" + "[" + j + "]" + " ");
                } else {
                    System.out.print(squares[i][j].toString() + "[" + i + "]" + "[" + j + "]" + " ");
                }
            }
            System.out.println();
        }
    }

    //TODO add more details here to make a move
    public String MowerTakeAction(int MowerID, String mowerAction, Point newPoint) {

        switch (mowerAction) {
            case "move": {
                String response = mowers.get(MowerID).Move(newPoint, "grass");
                if (mowers.get(MowerID).hasCrashed) {
                    crashedMowerCounter++;
                } else {
                    squares[newPoint.x][newPoint.y] = new Empty(newPoint);
                }
                return response;
            }
            case "steer": {
                return mowers.get(MowerID).Steer(Direction.east);

            }
            case "pass": {
                return mowers.get(MowerID).Pass();

            }
            case "scan": {
                return mowers.get(MowerID).Scan(null, null);

            }
            default:
                return "invalid option";

        }


    }

    public int Area() {
        return this.height * this.width;
    }

    public void MarkGrassAsCut(int MowerID) {
        mowers.get(MowerID).IncrementCutGrassSquare();
    }

    public boolean CountAllGrassCut() {
        int currentCutSquares = 0;

        for (Mower mower : mowers) {
            currentCutSquares += mower.countCutGrass;
        }

        return currentCutSquares == totalGrassSquares;
    }


}