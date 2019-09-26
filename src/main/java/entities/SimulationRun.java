package entities;

import enums.Direction;
import enums.Strategy;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class SimulationRun {
    public int maxTurns;
    public List<String> fileInformation;
    public Lawn lawn;
    public int countTurns = 0;
    public int countMowerCrash = 0;

    public SimulationRun(List<String> file) {
        this.fileInformation = file;
        ProcessFileInformation();
        RunSimulation(0);


    }

    private void ProcessFileInformation() {

        int width = Integer.parseInt(fileInformation.get(0));

        int height = Integer.parseInt(fileInformation.get(1));

        int numMowers = Integer.parseInt(fileInformation.get(2));
        ArrayList<Mower> mowers = new ArrayList<Mower>();

        for (int mower = 0; mower < numMowers; mower++) {

            String[] line = fileInformation.get(mower + 3).split(",");
            Mower newMower = new Mower(new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]))
                    , Direction.valueOf(line[2]), Integer.parseInt(line[3]));
            mowers.add(newMower);

        }


        int numCraters = Integer.parseInt(fileInformation.get(3 + numMowers));
        ArrayList<Crater> craters = new ArrayList<Crater>();
        for (int crater = 0; crater < numCraters; crater++) {

            String[] line = fileInformation.get(4 + numMowers + crater).split(",");
            Point point = new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            Crater newCrater = new Crater(point);
            craters.add(newCrater);


        }

        lawn = new Lawn(height,width,mowers,craters);

        int max_turns = Integer.parseInt(fileInformation.get(fileInformation.size() - 1));
        maxTurns = max_turns;


    }

    private void RunSimulation(int mowerID) {

        if (!AllGrassCut() || !MaxTurnsReached() || !AllMowersCrashed()) {

            if (lawn.mowers.get(mowerID).strategy == Strategy.RANDOM) {

                RunRandomStrategy(mowerID);
            } else {

                RunMyStrategy(mowerID);

            }


        } else {
            HaltSimulationRun();
        }


    }

    public void RunRandomStrategy(int mowerID) {


    }


    public void RunMyStrategy(int mowerID) {
    }


    private void UpdateTurnCounter() {
        countTurns++;
    }

    private void UpdateMowerCrashCounter() {
        countMowerCrash++;
    }

    private boolean AllMowersCrashed() {
        return countMowerCrash == lawn.mowers.size();
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
