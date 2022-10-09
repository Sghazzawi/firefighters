package main.firefighters;

import main.api.CityNode;
import static java.lang.Math.abs;


public final class Utils {
    public static Integer getManhattanDistance(CityNode start, CityNode end) {
        return abs(end.getX() - start.getX()) + abs(end.getY()-start.getY());
    }
}
