import action.Action;

import entities.SimulationRun;

import utils.MapUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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


        //list.forEach(System.out::println);

        SimulationRun simulationRun = new SimulationRun(list);

        System.out.println("SimulationRun Details");

        for  (int j = simulationRun.lawn.height - 1; j >= 0; --j){

            for (int i = 0; i < simulationRun.lawn.width; ++i) {

                System.out.print(simulationRun.lawn.squares[i][j].description+ "["+i+"]"+"["+j+"]"+" ");
            }
            System.out.println();
        }

        System.out.println(simulationRun.lawn.totalGrassSquares);
        simulationRun.lawn.mowers.get(0).Scan(simulationRun.lawn.squares);



        //TODO: run a for loop to max turns or number of mowers that not yet crashed and compute action for each of them + evaluate

        //Propose action
        Action proposedAction = new Strategist(simulationRun).proposeAction();
        //System.out.println(proposedAction.render());
        //Evaluate action
        String actionOutcome = new MapUtils().evaluateAction(simulationRun, proposedAction);
       // System.out.println(actionOutcome);


    }
}
