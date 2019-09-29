package entities;

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

    public Lawn(int height, int width, List<Mower> mowers, List<Crater> craters, List<Strategy> strategies) {
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
        totalGrassSquares = totalGrassSquares + mowers.size();
    }

    public void GetStateInformationOfSquares() {

        for (int j = height - 1; j >= 0; --j) {

            for (int i = 0; i < width; ++i) {

                Point currentPoint = new Point(i, j);
                boolean mowerPosition = false;

                for (Mower m : mowers) {
                    if (m.coordinate.equals(currentPoint)) {
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

    public int Area() {
        return this.height * this.width;
    }

    public boolean CountAllGrassCut() {
        int currentCutSquares = 0;

        for (Mower mower : mowers) {
            currentCutSquares += mower.countCutGrass;
        }

        return currentCutSquares == totalGrassSquares;
    }

}