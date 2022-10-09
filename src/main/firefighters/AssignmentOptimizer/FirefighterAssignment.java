package main.firefighters.AssignmentOptimizer;

import main.api.Building;
import main.api.CityNode;
import main.firefighters.FirefighterImpl;

public class FirefighterAssignment {
    private final FirefighterBuildingPair pair;
    private final AssignmentOptimizer optimizer;

    public FirefighterAssignment(FirefighterImpl firefighter,
                                 Building building,
                                 AssignmentOptimizer optimizer) {
        this.optimizer = optimizer;
        this.pair = new FirefighterBuildingPair(firefighter, building);
    }

    public FirefighterImpl getFirefighter() {
        return pair.getFirefighter();
    }

    public Building getBuilding() {
        return pair.getBuilding();
    }

    public void execute(AssignmentExecution execution) {
        FirefighterImpl firefighter = pair.getFirefighter();
        Building building = pair.getBuilding();
        CityNode oldFirefighterLocation = new CityNode(firefighter.getLocation().getX()
                , firefighter.getLocation().getY());
        execution.execute(firefighter, building);
        if (null != optimizer) {
            optimizer.update(oldFirefighterLocation, firefighter, building);
        }
    }
}
