package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.Node;
import main.firefighters.CityNodeTree.api.Tree;

import java.util.List;

public class CityNodeTree implements Tree {
    private Node rootNode;
    private Integer size;

    public CityNodeTree(List<CityNode> cityNodes) {
        this.size = cityNodes.size();
        this.rootNode = new XNode(cityNodes, 0, null);
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public Boolean contains(CityNode cityNode) {
        return rootNode != null ? rootNode.contains(cityNode) : false;
    }

    @Override
    public CityNode findNearestNeighbor(CityNode cityNode) {
        return rootNode.findNearestNeighbor(cityNode).getCityNode();
    }

    @Override
    public void remove(CityNode cityNode) {
        if (rootNode.remove(cityNode)) {
            size -= 1;
        }
        if (size == 0) {
            rootNode = null;
        }
    }
}
