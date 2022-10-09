package main.firefighters.CityNodeTree.api;

import main.api.CityNode;

public interface ChildrenProvider {
    Node getLeft();
    Node getRight();
    CityNode getCityNode();
}
