package main.firefighters;

import main.api.CityNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirefighterImplTest {

    @Test
    public void testBasicMovements() {
        CityNode startLocation = new CityNode(0, 0);
        FirefighterImpl firefighter = new FirefighterImpl(startLocation);
        assertEquals(0,firefighter.distanceTraveled());
        assertEquals(startLocation, firefighter.getLocation());

        CityNode location1 = new CityNode(1, 0);
        firefighter.moveTo(location1);
        assertEquals(1,firefighter.distanceTraveled());
        assertEquals(location1, firefighter.getLocation());


        CityNode location2 = new CityNode(1, 2);
        firefighter.moveTo(location2);
        assertEquals(3,firefighter.distanceTraveled());
        assertEquals(location2, firefighter.getLocation());

        CityNode location3 = new CityNode(0, 2);
        firefighter.moveTo(location3);
        assertEquals(4,firefighter.distanceTraveled());
        assertEquals(location3, firefighter.getLocation());

        CityNode location4 = new CityNode(0, 0);
        firefighter.moveTo(location4);
        assertEquals(6,firefighter.distanceTraveled());
        assertEquals(location4, firefighter.getLocation());

        CityNode location5 = new CityNode(5, 5);
        firefighter.moveTo(location5);
        assertEquals(16,firefighter.distanceTraveled());
        assertEquals(location5, firefighter.getLocation());

        CityNode location6 = new CityNode(3, 3);
        firefighter.moveTo(location6);
        assertEquals(20,firefighter.distanceTraveled());
        assertEquals(location6, firefighter.getLocation());



    }


}