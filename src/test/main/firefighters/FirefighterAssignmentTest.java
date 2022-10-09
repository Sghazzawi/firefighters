package main.firefighters;

import main.api.Building;
import main.api.CityNode;
import main.api.exceptions.FireproofBuildingException;
import main.api.exceptions.NoFireFoundException;
import main.firefighters.AssignmentOptimizer.FirefighterAssignment;
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

        FirefighterAssignment firefighterAssignment = new FirefighterAssignment(firefighter, building, null);
        firefighterAssignment.execute((ff, b) -> {
            ff.moveTo(b.getLocation());
            try {
                b.extinguishFire();
            } catch (NoFireFoundException e) {
                e.printStackTrace();
            }
        });

        assertEquals(buildingLocation,firefighter.getLocation());
        assertEquals(10, firefighter.distanceTraveled());
        assertFalse(building.isBurning());
    }
}