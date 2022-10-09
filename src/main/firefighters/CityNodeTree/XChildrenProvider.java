package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.ChildrenProvider;
import main.firefighters.CityNodeTree.api.Node;

import java.util.Comparator;
import java.util.List;

class XChildrenProvider implements ChildrenProvider {
    private YNode left;
    private YNode right;
    private CityNode cityNode;

    public XChildrenProvider(List<CityNode> cityNodes,
                             int depth,
                             XNode node) {
        if (cityNodes.size() == 1) {
            this.cityNode = cityNodes.get(0);
        } else if (cityNodes.size() == 2) {
            this.cityNode = cityNodes.get(1);
            this.left = new YNode(cityNodes.subList(0, 1), depth + 1, node);
        } else if (cityNodes.size() > 2) {
            Comparator<CityNode> xComparator = Comparator.comparingInt(CityNode::getX);
            cityNodes.sort(xComparator);
            int index = cityNodes.size() / 2;
            this.cityNode = cityNodes.get(index);
            this.left = new YNode(cityNodes.subList(0, index), depth + 1, node);
            this.right = new YNode(cityNodes.subList(index + 1, cityNodes.size()), depth + 1, node);
        }
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    @Override
    public CityNode getCityNode() {
        return cityNode;
    }
}
