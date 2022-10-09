package main.firefighters;

import main.api.Building;
import main.api.exceptions.NoFireFoundException;

class FirefighterAssignment {
    private final FirefighterImpl firefighter;
    private final Building building;

    public FirefighterAssignment(FirefighterImpl firefighter, Building building) {
        this.firefighter = firefighter;
        this.building = building;
    }

    public FirefighterImpl getFirefighter() {
        return firefighter;
    }

    public Building getBuilding() {
        return building;
    }

    public void execute() {
        try {
            firefighter.moveTo(building.getLocation());
            building.extinguishFire();
        } catch (NoFireFoundException e) {
            e.printStackTrace();
        }
    }
}
