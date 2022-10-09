package main.firefighters;

import java.util.*;
import java.util.stream.Collectors;

import com.sun.tools.javac.util.Pair;
import main.api.City;
import main.api.CityNode;
import main.api.FireDispatch;
import main.api.Firefighter;
import main.firefighters.CityNodeTree.CityNodeTree;
import main.firefighters.CityNodeTree.api.Tree;

public class FireDispatchImpl implements FireDispatch {
   private final List<FirefighterImpl> firefighters = new ArrayList<>();
    private Map<CityNode, List<FirefighterImpl>> firefightersByCityNode = Collections.emptyMap();
    private final City city;

    public FireDispatchImpl(City city) {
        this.city = city;
    }

    @Override
    public void setFirefighters(int numFirefighters) {
        for (int i = 0; i < numFirefighters; i++) {
            FirefighterImpl firefighter = new FirefighterImpl(city.getFireStation().getLocation());
            firefighters.add(firefighter);
        }
        firefightersByCityNode = firefighters.stream().collect(Collectors.groupingBy(FirefighterImpl::getLocation));
    }

    @Override
    public List<Firefighter> getFirefighters() {
        return Collections.unmodifiableList(firefighters);
    }

    @Override
    public void dispatchFirefighters(CityNode... burningBuildings) {
        if (!firefighters.isEmpty()) {
            List<CityNode> cityNodes = Arrays.stream(burningBuildings).filter(it -> city.getBuilding(it).isBurning()).collect(Collectors.toList());
            CityNodeTree cityNodeTree = new CityNodeTree(cityNodes);
            while (cityNodeTree.size() > 0) {
                FirefighterAssignment assignment = findOptimalAssignment(cityNodeTree);

                List<FirefighterImpl> firefighters = firefightersByCityNode.get(assignment.getFirefighter().getLocation());
                firefighters.remove(assignment.getFirefighter());
                if (firefighters.isEmpty()) {
                    firefightersByCityNode.remove(assignment.getFirefighter().getLocation(), firefighters);
                }

                assignment.execute();

                cityNodeTree.remove(assignment.getBuilding().getLocation());
                firefighters = firefightersByCityNode.get(assignment.getFirefighter().getLocation());
                if (null == firefighters) {
                    firefightersByCityNode.put(assignment.getFirefighter().getLocation(), new ArrayList<>(Collections.singletonList(assignment.getFirefighter())));
                }
            }
        }
    }

    private FirefighterAssignment findOptimalAssignment(Tree burningBuildings) {
        Pair<CityNode, FirefighterImpl> shortestDistancePair = null;

        for (CityNode cityNode : firefightersByCityNode.keySet()) {
            CityNode building = burningBuildings.findNearestNeighbor(cityNode);
            FirefighterImpl firefighter = firefightersByCityNode.get(cityNode).get(0);
            if (null == shortestDistancePair) {
                shortestDistancePair = Pair.of(building, firefighter);
            } else {
                if (Utils.getManhattanDistance(firefighter.getLocation(), building) <
                        Utils.getManhattanDistance(shortestDistancePair.snd.getLocation(), shortestDistancePair.fst)) {
                    shortestDistancePair = Pair.of(building, firefighter);
                }
            }

        }

        return new FirefighterAssignment(shortestDistancePair.snd, city.getBuilding(shortestDistancePair.fst));
    }


}
