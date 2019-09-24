package utils;

import action.Action;
import entities.SimulationRun;

import java.util.List;

public class MapUtils {
    //TODO: print the current state
    public void printMap() {
        System.out.println("THIS IS THE MAP");
    }




    //TODO: maybe make outcome an enum (crash and ok)
    public String evaluateAction(SimulationRun simulationRun, Action action) {
        //TODO: logic to determine if action is allowed
        return "ok";
    }
}
