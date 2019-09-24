import action.Action;
import action.PassAction;
import entities.SimulationRun;

public class Strategist {
    public SimulationRun simulationRun;

    public Strategist(SimulationRun simulationRun) {
        this.simulationRun = simulationRun;
    }

    public Action proposeAction() {
        //TODO: loop over each mower and compute an action for each of them
        //TODO: check the strategy, if random -> compute random move else your own logic
        return new PassAction();
    }
}
