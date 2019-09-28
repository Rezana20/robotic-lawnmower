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


    public SimulationRun(List<String> file) {

        randGenerator = new Random();
        this.fileInformation = file;
        ProcessFileInformation();
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
        boolean maxedTurns = MaxTurnsReached();
        boolean allmowers = AllMowersCrashed();
        boolean allgrasscut = AllGrassCut();

        while (!AllMowersCrashed()) {

            if(MaxTurnsReached() || AllGrassCut()) {
                break;
            }

            for (int i = 0; i < lawn.mowers.size(); i++) {

                //if (!lawn.mowers.get(i).hasCrashed && !MaxTurnsReached() || !lawn.mowers.get(i).hasCrashed && !AllMowersCrashed() || !lawn.mowers.get(i).hasCrashed && !AllGrassCut()) {
                if(!lawn.mowers.get(i).hasCrashed && !AllMowersCrashed()) {
                    UpdateTurnCounter();
                    Strategy strat = lawn.strategies.get(i);

                    if (strat == Strategy.random) {
                        RunRandomStrategy(i);

                    } else {

                    }
                }
                else {
                    UpdateTurnCounter();

                }


            }


        }

        HaltSimulationRun();


    }

    public void RunMyStrategy(int mowerID) {

        //TODO use a greedy algorithm

        //greedy is  - make a move if it is valid to cut
        //else determine closest grass sqaure and make a valid move towards it.

    }


    public void RunRandomStrategy(int mowerID) {

        int moveRandomChoice = randGenerator.nextInt(100);
        Mower myMower = lawn.mowers.get(mowerID);
        if (moveRandomChoice < 10) {

            System.out.println(myMower.Pass());

        } else if (moveRandomChoice < 35) {

            Scan(mowerID);

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

    private void Scan(int mowerID) {
        List<Point> mowerPoints = new ArrayList<Point>();
        for (Mower currentMower : lawn.mowers) {

            if (!currentMower.hasCrashed) {
                mowerPoints.add(currentMower.coordinate);
            }
        }
        System.out.println(lawn.mowers.get(mowerID).Scan(lawn.squares, mowerPoints));
    }

    private void Move(Mower mower) {

        Point newCoordinate = new Point();
        newCoordinate = DetermineNewPoint(mower.coordinate, mower.currentDirection);

        //only do this for valid cells
        if (mower.isValid(newCoordinate.x,newCoordinate.y,lawn.width,lawn.height)) {

            System.out.println(mower.Move(newCoordinate, lawn.squares[newCoordinate.x][newCoordinate.y].description));

            UpdateState(newCoordinate);
        }
        else {
            System.out.println(mower.Move(newCoordinate, "fence"));

        }
    }

    private void UpdateState(Point updatePoint) {

        String oldSquareDetail = lawn.squares[updatePoint.x][updatePoint.y].description;

        switch (oldSquareDetail) {
            case "empty": {
                lawn.squares[updatePoint.x][updatePoint.y] = new Empty(updatePoint);
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
        for(Mower mower: lawn.mowers) {
            if(mower.hasCrashed) {
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
