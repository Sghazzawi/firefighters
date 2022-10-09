package main.firefighters.CityNodeTree.api;

import main.api.CityNode;

public interface Tree {
    long size();
    Boolean contains(CityNode cityNode);
    CityNode findNearestNeighbor(CityNode cityNode);
    void remove(CityNode cityNode);
}
