package entities;


import enums.Direction;
import enums.Strategy;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static enums.Direction.*;

@Data
public class SimulationRun {

    public int maxTurns;
    public List<String> fileInformation;
    public Lawn lawn;
    public int countTurns = 0;
    private static Random randGenerator;
    private Point[] ClosestGrassToMower;


    public SimulationRun(List<String> file) {

        randGenerator = new Random();
        this.fileInformation = file;
        ProcessFileInformation();

        ClosestGrassToMower = new Point[lawn.mowers.size()];
        RunSimulation();


    }

    private void ProcessFileInformation() {

        int width = Integer.parseInt(fileInformation.get(0));

        int height = Integer.parseInt(fileInformation.get(1));

        int numMowers = Integer.parseInt(fileInformation.get(2));
        ArrayList<Mower> mowers = new ArrayList<Mower>();
        List<Strategy> strategies = new ArrayList<Strategy>();

        for (int mower = 0; mower < numMowers; mower++) {

            String[] line = fileInformation.get(mower + 3).split(",");
            Mower newMower = new Mower("m" + mower, new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]))
                    , valueOf(line[2]));
            mowers.add(newMower);
            strategies.add(Strategy.get((line[3])));
        }

        int numCraters = Integer.parseInt(fileInformation.get(3 + numMowers));
        ArrayList<Crater> craters = new ArrayList<Crater>();
        for (int crater = 0; crater < numCraters; crater++) {

            String[] line = fileInformation.get(4 + numMowers + crater).split(",");
            Point point = new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            Crater newCrater = new Crater(point);
            craters.add(newCrater);


        }

        lawn = new Lawn(height, width, mowers, craters, strategies);

        int max_turns = Integer.parseInt(fileInformation.get(fileInformation.size() - 1));
        maxTurns = max_turns;


    }

    private void RunSimulation() {

        while (!AllMowersCrashed() || !MaxTurnsReached() || !AllGrassCut()) {

            for (int i = 0; i < lawn.mowers.size(); i++) {

                if (!lawn.mowers.get(i).hasCrashed && !AllMowersCrashed()) {
                    UpdateTurnCounter();
                    Strategy strat = lawn.strategies.get(i);

                    if (strat == Strategy.random) {
                        RunRandomStrategy(i);

                    } else {
                        RunMyStrategy(i);
                    }
                } else {
                    UpdateTurnCounter();

                }

                if (MaxTurnsReached() || AllGrassCut() || AllMowersCrashed()) {
                    break;
                }

            }

            if (MaxTurnsReached() || AllGrassCut() || AllMowersCrashed()) {
                break;
            }
        }


        HaltSimulationRun();
    }

    public void RunMyStrategy(int mowerID) {

        Mower mower = lawn.mowers.get(mowerID);

        List<Point> grassPoints = new ArrayList<Point>();
        for (int x = 0; x < lawn.width; x++) {
            for (int y = 0; y < lawn.height; y++) {

                if (lawn.squares[x][y].toString().equals("grass")) {
                    grassPoints.add(new Point(x, y));
                }
            }
        }

        GetClosestGrass(mowerID, mower, grassPoints);

        Point closestGrass = ClosestGrassToMower[mowerID];
        Direction dir = IsMowerInCorrectDirection(closestGrass, mower);
        if (dir.equals(mower.currentDirection)) {
            Move(mower);
        } else {
            System.out.println(mower.Steer(dir));
        }

    }

    private Direction IsMowerInCorrectDirection(Point targetPoint, Mower mower) {
        Direction currentDirection = mower.currentDirection;

        if (targetPoint.x == mower.coordinate.x && targetPoint.y > mower.coordinate.y) {
            currentDirection = north;
        } else if (targetPoint.x == mower.coordinate.x && targetPoint.y < mower.coordinate.y) {
            currentDirection = south;
        } else if (targetPoint.x < mower.coordinate.x && targetPoint.y == mower.coordinate.y) {
            currentDirection = west;
        } else if (targetPoint.x > mower.coordinate.x && targetPoint.y == mower.coordinate.y) {
            currentDirection = east;
        } else if (targetPoint.x > mower.coordinate.x && targetPoint.y < mower.coordinate.y) {
            currentDirection = southwest;
        } else if (targetPoint.x > mower.coordinate.x && targetPoint.y > mower.coordinate.y) {
            currentDirection = northwest;
        } else if (targetPoint.x > mower.coordinate.x && targetPoint.y > mower.coordinate.y) {
            currentDirection = northeast;
        } else if (targetPoint.x > mower.coordinate.x && targetPoint.y < mower.coordinate.y) {
            currentDirection = southeast;
        }

        return currentDirection;

    }

    private void GetClosestGrass(int id, Mower mower, List<Point> grassPoints) {

        Point minPoint = new Point();
        int i1 = mower.coordinate.x;
        int j1 = mower.coordinate.y;

        int minDistance = lawn.height * lawn.width;

        for (Point grassPoint : grassPoints) {

            int distance = Math.abs(i1 - grassPoint.x) + Math.abs(j1 - grassPoint.y);
            minDistance = Math.min(distance, minDistance);

            if (minDistance == distance) {

                minPoint.x = grassPoint.x;
                minPoint.y = grassPoint.y;

                ClosestGrassToMower[id] = minPoint;
            }

        }


    }

    private void RunRandomStrategy(int mowerID) {

        int moveRandomChoice = randGenerator.nextInt(100);
        Mower myMower = lawn.mowers.get(mowerID);
        if (moveRandomChoice < 10) {

            System.out.println(myMower.Pass());

        } else if (moveRandomChoice < 35) {

            Scan(myMower);

        } else if (moveRandomChoice < 60) {

            Steer(myMower);

        } else {

            Move(myMower);
        }

    }

    private void Steer(Mower mower) {

        Direction newDirection;

        newDirection = RandomDirection();

        while (newDirection == mower.currentDirection) {
            newDirection = RandomDirection();
        }

        System.out.println(mower.Steer(newDirection));

    }

    private void Scan(Mower mower) {

        List<Point> mowerPoints = new ArrayList<Point>();
        for (Mower currentMower : lawn.mowers) {

            if (!currentMower.hasCrashed) {
                mowerPoints.add(currentMower.coordinate);
            }
        }
        System.out.println(mower.Scan(lawn.squares, mowerPoints));
    }

    private void Move(Mower mower) {

        Point newCoordinate = new Point();
        newCoordinate = DetermineNewPoint(mower.coordinate, mower.currentDirection);

        //only do this for valid cells
        if (mower.isValid(newCoordinate.x, newCoordinate.y, lawn.width, lawn.height)) {

            System.out.println(mower.Move(newCoordinate, lawn.squares[newCoordinate.x][newCoordinate.y].toString()));

            UpdateState(newCoordinate);
        } else {
            System.out.println(mower.Move(newCoordinate, "fence"));

        }
    }

    private void UpdateState(Point updatePoint) {

        String oldSquareDetail = lawn.squares[updatePoint.x][updatePoint.y].toString();

        switch (oldSquareDetail) {
            case "crater": {
                break;
            }
            case "empty": {
                break;
            }
            case "grass": {
                lawn.squares[updatePoint.x][updatePoint.y] = new Empty(updatePoint);
                break;
            }
            case "mower": {
                //wreckage
                for (int i = 0; i < lawn.mowers.size(); i++) {

                    if (lawn.mowers.get(i).coordinate.equals(updatePoint)) {

                        if (!lawn.mowers.get(i).hasCrashed) {
                            lawn.mowers.get(i).hasCrashed = true;
                            System.out.println(lawn.mowers.get(i).mowerId + "\ncrashed");
                        }
                    }
                }
                lawn.squares[updatePoint.x][updatePoint.y] = new Empty(updatePoint);
                break;
            }
            default:
                System.out.println(oldSquareDetail);
                System.out.println("unknown");
                break;

        }

    }

    private Point DetermineNewPoint(Point oldPoint, Direction currentDirection) {

        Point newPoint = new Point();

        switch (currentDirection) {

            case north: {
                newPoint.x = oldPoint.x;
                newPoint.y = oldPoint.y + 1;
                break;
            }
            case northeast: {
                newPoint.x = oldPoint.x + 1;
                newPoint.y = oldPoint.y + 1;
                break;
            }
            case east: {
                newPoint.x = oldPoint.x + 1;
                newPoint.y = oldPoint.y;
                break;
            }
            case southeast: {
                newPoint.x = oldPoint.x + 1;
                newPoint.y = oldPoint.y - 1;
                break;
            }
            case south: {
                newPoint.x = oldPoint.x;
                newPoint.y = oldPoint.y - 1;
                break;
            }
            case southwest: {
                newPoint.x = oldPoint.x - 1;
                newPoint.y = oldPoint.y - 1;
                break;
            }
            case west: {
                newPoint.x = oldPoint.x - 1;
                newPoint.y = oldPoint.y;
                break;
            }
            case northwest: {
                newPoint.x = oldPoint.x - 1;
                newPoint.y = oldPoint.y + 1;
                break;
            }
            default: {
                break;
            }
        }

        return newPoint;

    }

    private void UpdateTurnCounter() {
        countTurns++;
    }

    private boolean AllMowersCrashed() {
        int crashCounter = 0;
        for (Mower mower : lawn.mowers) {
            if (mower.hasCrashed) {
                crashCounter++;
            }
        }

        return crashCounter == lawn.mowers.size();
    }

    private boolean MaxTurnsReached() {
        return countTurns == maxTurns;
    }

    private boolean AllGrassCut() {
        return lawn.CountAllGrassCut();
    }

    private void HaltSimulationRun() {

        int currentCutSquares = 0;

        for (Mower mower : lawn.mowers) {
            currentCutSquares += mower.countCutGrass;
        }
        Report report = new Report(lawn.Area(), lawn.totalGrassSquares, currentCutSquares, countTurns);
        report.Display();

    }

}
