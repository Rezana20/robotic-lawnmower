import entities.SimulationRun;

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
            SimulationRun simulationRun = new SimulationRun(list);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("requires a csv file");
        }
    }
}
