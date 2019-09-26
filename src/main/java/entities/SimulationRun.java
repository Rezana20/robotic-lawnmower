package entities;

import enums.Direction;
import enums.Strategy;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class SimulationRun {
    public int maxTurns;
    public List<String> fileInformation;
    public Lawn lawn;
    public int countTurns = 0;

    public SimulationRun(List<String> file) {
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
            Mower newMower = new Mower("m"+mower ,new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]))
                    , Direction.valueOf(line[2]));
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

        lawn = new Lawn(height,width,mowers,craters,strategies);

        int max_turns = Integer.parseInt(fileInformation.get(fileInformation.size() - 1));
        maxTurns = max_turns;


    }

    private void RunSimulation() {




        while (MaxTurnsReached() || AllMowersCrashed() || AllGrassCut()) {

            for(int i = 0; i < lawn.mowers.size(); i++) {
                UpdateTurnCounter();
                //lawn.mowers.get(i).hasCrashed = true;
                if(false) {
                //if(!lawn.mowers.get(i).hasCrashed) {
                    Strategy strat = lawn.strategies.get(i);


                    if(strat == Strategy.random) {

                        //make a random move
                        if(AllGrassCut() || MaxTurnsReached() || AllMowersCrashed()) {
                            break;
                        }
                    }
                    else {

                        //make a strategic move
                        if(AllGrassCut() || MaxTurnsReached() || AllMowersCrashed()) {
                            break;
                        }

                    }
                }
            }


        }
            HaltSimulationRun();

        //TODO build the report
    }

    public void RunRandomStrategy(int mowerID) {


    }


    public void RunMyStrategy(int mowerID) {
    }


    //turn is an action  (move, steer, scan, pass)
    private void UpdateTurnCounter() {
        countTurns++;
    }


    private boolean AllMowersCrashed() {
        return lawn.crashedMowerCounter == lawn.mowers.size();
    }

    private boolean MaxTurnsReached() {
        return countTurns == maxTurns;
    }

    private boolean AllGrassCut() {
        return lawn.CountAllGrassCut();
    }

    public void HaltSimulationRun() {

        int currentCutSquares = 0;

        for (Mower mower : lawn.mowers) {
            currentCutSquares += mower.countCutGrass;
        }


        Report report = new Report(lawn.Area(), lawn.totalGrassSquares, currentCutSquares, countTurns);
        report.Display();

    }


}
