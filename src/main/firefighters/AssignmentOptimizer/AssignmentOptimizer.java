package main.firefighters.AssignmentOptimizer;

import main.api.Building;
import main.api.CityNode;
import main.api.Firefighter;
import main.firefighters.FirefighterImpl;

import java.util.List;

public interface AssignmentOptimizer {
    void addFireFighter(FirefighterImpl firefighter);
    AssignmentIterator getAssignmentIterator(List<CityNode> cityNodes);
    FirefighterAssignment findOptimalAssignment();
    Boolean fireCanBeExtinguished();
    void update(CityNode oldLocation, FirefighterImpl firefighter, Building building);
    List<Firefighter> getFireFighters();
}
