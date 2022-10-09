package main.firefighters.CityNodeTree.api;

import main.api.CityNode;

import java.util.List;

public interface Node {
    CityNode getCityNode();
    void setCityNode(CityNode cityNode);
    Node getParent();
    Node getLeft();
    Node getRight();
    Boolean isLeafNode();
    Boolean isRoot();
    void disassociate(Node node);
    Node findNearestNeighbor(CityNode cityNode);
    Boolean contains(CityNode cityNode);
    List<Node> getChildren();
    Boolean remove(CityNode cityNode);
    Integer distanceTo(CityNode cityNode);
}
