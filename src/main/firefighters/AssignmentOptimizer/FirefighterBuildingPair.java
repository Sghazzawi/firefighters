package main.firefighters.AssignmentOptimizer;

import main.api.Building;
import main.firefighters.FirefighterImpl;

public class FirefighterBuildingPair {
    private final FirefighterImpl firefighter;
    private final Building building;


    public FirefighterBuildingPair(FirefighterImpl firefighter, Building building) {
        this.firefighter = firefighter;
        this.building = building;
    }

    public FirefighterImpl getFirefighter() {
        return firefighter;
    }

    public Building getBuilding() {
        return building;
    }
}
