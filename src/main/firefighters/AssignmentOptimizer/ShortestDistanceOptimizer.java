package main.firefighters.AssignmentOptimizer;

import main.api.Building;
import main.api.City;
import main.api.CityNode;
import main.api.Firefighter;
import main.firefighters.CityNodeTree.CityNodeTree;
import main.firefighters.CityNodeTree.api.Tree;
import main.firefighters.FirefighterImpl;
import main.firefighters.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class ShortestDistanceOptimizer implements AssignmentOptimizer {

    private final Map<CityNode, List<FirefighterImpl>> firefightersByCityNode = new HashMap<>();
    private Tree tree;
    private final City city;

    public ShortestDistanceOptimizer(City city) {
        this.city = city;
    }

    @Override
    public void addFireFighter(FirefighterImpl firefighter) {
        List<FirefighterImpl> firefighters = firefightersByCityNode.computeIfAbsent(firefighter.getLocation(), k -> new ArrayList<>());
        firefighters.add(firefighter);
    }

    @Override
    public AssignmentIterator getAssignmentIterator(List<CityNode> cityNodes) {
        tree = new CityNodeTree(cityNodes);
        return new AssignmentIterator(this);
    }

    @Override
    public FirefighterAssignment findOptimalAssignment() {
        if (fireCanBeExtinguished()) {
            FirefighterBuildingPair shortestDistancePair = null;

            for (CityNode cityNode : firefightersByCityNode.keySet()) {
                CityNode buildingLocation = tree.findNearestNeighbor(cityNode);
                FirefighterImpl firefighter = firefightersByCityNode.get(cityNode).get(0);
                if (null == shortestDistancePair) {
                    shortestDistancePair = new FirefighterBuildingPair(firefighter,city.getBuilding(buildingLocation));
                } else {
                    if (Utils.getManhattanDistance(firefighter.getLocation(), buildingLocation) <
                            Utils.getManhattanDistance(shortestDistancePair.getFirefighter().getLocation(), shortestDistancePair.getBuilding().getLocation())) {
                        shortestDistancePair = new FirefighterBuildingPair(firefighter,city.getBuilding(buildingLocation));
                    }
                }

            }
            return new FirefighterAssignment(shortestDistancePair.getFirefighter(), shortestDistancePair.getBuilding(), this);
        }
        throw new NoSuchElementException("No fires can be extinguished");
    }

    @Override
    public void update(CityNode oldLocation, FirefighterImpl firefighter, Building building) {
        if (! building.isBurning()) {
            tree.remove(building.getLocation());
        }
        List<FirefighterImpl> firefighters = firefightersByCityNode.get(oldLocation);
        firefighters.remove(firefighter);
        if (firefighters.isEmpty()){
            firefightersByCityNode.remove(oldLocation);
        }
        addFireFighter(firefighter);
    }

    @Override
    public Boolean fireCanBeExtinguished() {
        return(!firefightersByCityNode.isEmpty() &&
                null != tree &&
                tree.size() > 0);
    }

    @Override
    public List<Firefighter> getFireFighters() {
        return this.firefightersByCityNode.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
