import action.Action;

import entities.SimulationRun;

import utils.MapUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<String> list;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("requires a csv file");
        }


        SimulationRun simulationRun = new SimulationRun(list);

       /* System.out.println("SimulationRun Details");

        simulationRun.lawn.DisplayStateInformationOfSquares();


        System.out.println();
        System.out.println(simulationRun.lawn.totalGrassSquares);

        System.out.println();

        List<Point> otherMowers = new ArrayList<Point>();
        otherMowers.add(simulationRun.lawn.mowers.get(1).coordinate);


        System.out.println(simulationRun.lawn.mowers.get(0).Scan(simulationRun.lawn.squares, otherMowers));


        System.out.println(simulationRun.lawn.mowers.get(0).Pass());

        //move to 1,2

        //System.out.println(simulationRun.lawn.mowers.get(0).Move(new Point(1,2),"mower"));
        //System.out.println(simulationRun.lawn.MowerTakeAction(0, "move", new Point(1, 2)));

        System.out.println("SimulationRun Details");

        simulationRun.lawn.DisplayStateInformationOfSquares();

        for (int i = 0; i < simulationRun.lawn.strategies.size(); i++) {
            System.out.println(i);
        }


        //TODO: run a for loop to max turns or number of mowers that not yet crashed and compute action for each of them + evaluate

        //Propose action
        Action proposedAction = new Strategist(simulationRun).proposeAction();
        //System.out.println(proposedAction.render());
        //Evaluate action
        String actionOutcome = new MapUtils().evaluateAction(simulationRun, proposedAction);
        // System.out.println(actionOutcome);

        */



    }
}
