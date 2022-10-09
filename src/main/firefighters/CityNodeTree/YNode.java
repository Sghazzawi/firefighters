package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.Node;

import java.util.Comparator;
import java.util.List;

class YNode extends BaseNode {
    private final static Comparator<Node> yNodeComparator = Comparator
            .comparingInt(a -> a.getCityNode().getX());

    public YNode(List<CityNode> cityNodes, int depth, Node parent) {
        super(parent, yNodeComparator, CityNode::getY);
        if (depth % 2 != 1) {
            throw new IllegalArgumentException("Ynode cannot be created at an even depth");
        }
        YChildrenProvider childrenProvider = new YChildrenProvider(cityNodes, depth, this);
        this.cityNode = childrenProvider.getCityNode();
        this.left = childrenProvider.getLeft();
        this.right = childrenProvider.getRight();
    }
}
