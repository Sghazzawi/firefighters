package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.Node;

import java.util.Comparator;
import java.util.List;

class XNode extends BaseNode {
    private static final Comparator<Node> xNodeComparator = Comparator
            .comparingInt(a -> a.getCityNode().getX());

    public XNode(List<CityNode> cityNodes, int depth, Node parent) {
        super(parent, xNodeComparator, CityNode::getX);
        if (depth % 2 != 0) {
            throw new IllegalArgumentException("Xnode cannot be created at an odd depth");
        }
        XChildrenProvider childrenProvider = new XChildrenProvider(cityNodes, depth, this);
        this.cityNode = childrenProvider.getCityNode();
        this.left = childrenProvider.getLeft();
        this.right = childrenProvider.getRight();
    }
}
