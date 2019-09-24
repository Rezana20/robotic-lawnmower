package entities;

import enums.Direction;
import enums.Strategy;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class SimulationRun {
    public int MaxTurns;
    public List<String> fileInformation;
    public Lawn lawn;

    public SimulationRun(List<String> file) {
        this.fileInformation = file;
        ProcessFileInformation();
        RunSimulation();


    }

    private void ProcessFileInformation() {

        lawn = new Lawn();

        lawn.width = Integer.parseInt(fileInformation.get(0));
        lawn.height = Integer.parseInt(fileInformation.get(1));

        int num_mowers = Integer.parseInt(fileInformation.get(2));

        for (int i = 0; i < num_mowers; i++) {

            String[] line = fileInformation.get(i + 3).split(",");
            Mower mower = new Mower(new Point(Integer.parseInt(line[0]),Integer.parseInt(line[1]))
                    , Direction.valueOf(line[2]), Integer.parseInt(line[3]));
           lawn.Mowers.add(mower);
//            System.out.println(line);
        }


        int num_craters = Integer.parseInt(fileInformation.get(3 + num_mowers));
//        System.out.println("craters: " + num_craters);

        for (int j = 0; j < num_craters; j++) {
//
            String[] line = fileInformation.get(4 + num_mowers + j).split(",");
            Point point = new Point(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            lawn.Craters.add(new Crater(point));

//            System.out.println(line);
        }
        int max_turns = Integer.parseInt(fileInformation.get(fileInformation.size() - 1));
        MaxTurns = max_turns;


    }

    private void RunSimulation() {

        //Check strategy


        //while not halted  {
        //
        // Run rezana's strategy
        // }


        //print report

    }

    private void RunRandom() {

        //logic given code
    }

}
