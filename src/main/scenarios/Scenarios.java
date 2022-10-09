package main.scenarios;

import main.api.*;
import main.api.exceptions.FireproofBuildingException;
import main.impls.CityImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Scenarios {

    @Test
    public void testNoFirefighters() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode fireNode = new CityNode(0, 1);
        Pyromaniac.setFire(basicCity, fireNode);

        fireDispatch.dispatchFirefighters(fireNode);
        Assert.assertTrue(basicCity.getBuilding(fireNode).isBurning());
    }

    @Test
    public void testNoFire() {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode fireNode = new CityNode(0, 1);

        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighters(fireNode);
        Assert.assertFalse(basicCity.getBuilding(fireNode).isBurning());
        Assert.assertEquals(0, fireDispatch.getFirefighters().get(0).distanceTraveled());
    }

    @Test
    public void testNoFireEnRouteToFire() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();

        CityNode[] fireNodes = {
                new CityNode(0, 1),
                new CityNode(1, 2)};
        Pyromaniac.setFire(basicCity, fireNodes[1]);


        fireDispatch.setFirefighters(1);
        fireDispatch.dispatchFirefighters(fireNodes);
        Assert.assertEquals(3, fireDispatch.getFirefighters().get(0).distanceTraveled());
    }

    @Test
    public void twoFiresSameFirefighter() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(0, 0));
        FireDispatch fireDispatch = basicCity.getFireDispatch();
        int totalDistanceTraveled = 0;


        CityNode[] fireNodes = {
                new CityNode(0, 1),
                new CityNode(0, 2)};
        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(2);
        fireDispatch.dispatchFirefighters(fireNodes);
        List<Firefighter> firefighters = fireDispatch.getFirefighters();


        for (Firefighter firefighter : firefighters) {
            totalDistanceTraveled += firefighter.distanceTraveled();
        }

        Assert.assertEquals(2, totalDistanceTraveled);
        Assert.assertFalse(basicCity.getBuilding(fireNodes[0]).isBurning());
        Assert.assertFalse(basicCity.getBuilding(fireNodes[1]).isBurning());
        Assert.assertEquals(firefighters.stream().filter(it -> it.distanceTraveled() == 0).count(), 1);
        Assert.assertEquals(firefighters.stream().filter(it -> it.distanceTraveled() == 2).count(), 1);
    }


    @Test
    public void eightFiresTwoPerFirefighter() throws FireproofBuildingException {
        City basicCity = new CityImpl(5, 5, new CityNode(2, 2));
        FireDispatch fireDispatch = basicCity.getFireDispatch();
        int totalDistanceTraveled = 0;
        int expectedDistancePerFirefighter = 2;
        int expectedTotalDistance = 8;


        CityNode[] fireNodes = {
                new CityNode(2, 0),
                new CityNode(2, 1),
                new CityNode(2, 3),
                new CityNode(2, 4),

                new CityNode(0, 2),
                new CityNode(1, 2),
                new CityNode(3, 2),
                new CityNode(4, 2),
        };
        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(4);
        fireDispatch.dispatchFirefighters(fireNodes);
        List<Firefighter> firefighters = fireDispatch.getFirefighters();


        for (Firefighter firefighter : firefighters) {
            totalDistanceTraveled += firefighter.distanceTraveled();
        }

        long numFirefightersWithExpectedDistance = firefighters
                .stream().filter(it -> it.distanceTraveled() == expectedDistancePerFirefighter).count();

        Assert.assertEquals(expectedTotalDistance, totalDistanceTraveled);
        Assert.assertTrue(Arrays.asList(fireNodes)
                .stream().allMatch(it -> !basicCity.getBuilding(it).isBurning()));
        Assert.assertEquals(firefighters.size(), numFirefightersWithExpectedDistance);
    }

    @Test
    public void eightFiresTwoPerFirefighterDiagonal() throws FireproofBuildingException {
        City basicCity = new CityImpl(7, 7, new CityNode(3, 3));
        FireDispatch fireDispatch = basicCity.getFireDispatch();
        int totalDistanceTraveled = 0;
        int expectedDistancePerFirefighter = 4;
        int expectedTotalDistance = 16;


        CityNode[] fireNodes = {
                new CityNode(2, 0),
                new CityNode(3, 1),
                new CityNode(3, 5),
                new CityNode(4, 6),

                new CityNode(0, 2),
                new CityNode(1, 3),
                new CityNode(5, 3),
                new CityNode(6, 4),
        };
        Pyromaniac.setFires(basicCity, fireNodes);

        fireDispatch.setFirefighters(4);
        fireDispatch.dispatchFirefighters(fireNodes);
        List<Firefighter> firefighters = fireDispatch.getFirefighters();


        for (Firefighter firefighter : firefighters) {
            totalDistanceTraveled += firefighter.distanceTraveled();
        }


        long numFirefightersWithExpectedDistance = firefighters.stream().filter(it -> it.distanceTraveled() == expectedDistancePerFirefighter).count();

        Assert.assertEquals(expectedTotalDistance, totalDistanceTraveled);
        Assert.assertTrue(Arrays.asList(fireNodes).stream().allMatch(it -> !basicCity.getBuilding(it).isBurning()));
        Assert.assertEquals(firefighters.size(), numFirefightersWithExpectedDistance);
    }
}
