package main.firefighters;

import java.util.*;
import java.util.stream.Collectors;

import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.api.exceptions.NoFireFoundException;
import main.firefighters.AssignmentOptimizer.*;

public class FireDispatchImpl implements FireDispatch {
    private final City city;
    private final AssignmentOptimizer optimizer;

    public FireDispatchImpl(City city) {
        this.city = city;
        optimizer = new ShortestDistanceOptimizer(city);
    }

    @Override
    public void setFirefighters(int numFirefighters) {
        for (int i = 0; i < numFirefighters; i++) {
            FirefighterImpl firefighter = new FirefighterImpl(city.getFireStation().getLocation());
            optimizer.addFireFighter(firefighter);
        }
    }

    @Override
    public List<Firefighter> getFirefighters() {
        return optimizer.getFireFighters();
    }

    @Override
    public void dispatchFirefighters(CityNode... burningBuildings) {
        List<CityNode> cityNodes = Arrays.stream(burningBuildings).filter(it -> city.getBuilding(it).isBurning()).collect(Collectors.toList());
        AssignmentIterator assignmentIterator = optimizer.getAssignmentIterator(cityNodes);
        while (assignmentIterator.hasNext()) {
            FirefighterAssignment assignment = assignmentIterator.next();
            assignment.execute((firefighter, building) -> {
                try {
                    firefighter.moveTo(building.getLocation());
                    building.extinguishFire();
                } catch (NoFireFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
