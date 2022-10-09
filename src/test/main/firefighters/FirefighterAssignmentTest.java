package main.firefighters;

import main.api.Building;
import main.api.CityNode;
import main.api.Firefighter;
import main.api.exceptions.FireproofBuildingException;
import main.impls.BuildingImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirefighterAssignmentTest {

    @Test
    public void testExecute() throws FireproofBuildingException {
        CityNode buildingLocation = new CityNode(5, 5);
        CityNode firefighterLocation = new CityNode(0, 0);

        Building building = new BuildingImpl(buildingLocation);
        building.setFire();
        FirefighterImpl firefighter = new FirefighterImpl(firefighterLocation);

        FirefighterAssignment firefighterAssignment = new FirefighterAssignment(firefighter, building);
        firefighterAssignment.execute();

        assertEquals(buildingLocation,firefighter.getLocation());
        assertEquals(10, firefighter.distanceTraveled());
        assertFalse(building.isBurning());
    }
}