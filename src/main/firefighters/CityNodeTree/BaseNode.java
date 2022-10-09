package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.DimensionHandler;
import main.firefighters.CityNodeTree.api.Node;
import main.firefighters.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.StrictMath.abs;

public class BaseNode implements Node {
    protected CityNode cityNode;
    private Node parent;
    protected Node left;
    protected Node right;
    private final Comparator<Node> nodeComparator;
    private final DimensionHandler dimensionHandler;


    protected BaseNode(Node parent,
                       Comparator<Node> nodeComparator,
                       DimensionHandler dimensionHandler) {
        this.parent = parent;
        this.dimensionHandler = dimensionHandler;
        this.nodeComparator = nodeComparator;
    }

    @Override
    public CityNode getCityNode() {
        return cityNode;
    }

    @Override
    public void setCityNode(CityNode cityNode) {
        this.cityNode = cityNode;
    }

    @Override
    public Node getParent() {
        return parent;
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
    public Boolean isLeafNode() {
        return null == left && null == right;
    }

    @Override
    public Boolean isRoot() {
        return null == parent;
    }

    @Override
    public Boolean remove(CityNode cityNode) {
        if (cityNode.equals(this.getCityNode())) {
            if (this.isLeafNode()) {
                if (!this.isRoot()) {
                    this.getParent().disassociate(this);
                    this.parent = null;
                } else {
                    this.setCityNode(null);
                }
                return true;
            } else {
                Node replacement = this.getReplacement(cityNode);
                this.setCityNode(replacement.getCityNode());
                return replacement.remove(replacement.getCityNode());
            }
        } else {
            if (this.isLeafNode()) {
                return false;
            }
            return this.traverse(cityNode).stream().map(it -> it.remove(cityNode)).reduce((a, b) -> a || b).orElse(false);
        }
    }

    @Override
    public void disassociate(Node node) {
        if (null != left && left.equals(node)) {
            left = null;
        }
        if (null != right && right.equals(node)) {
            right = null;
        }
    }

    @Override
    public Node findNearestNeighbor(CityNode cityNode) {
        if (null == cityNode) return null;
        Node bestNode = this;
        List<Node> firstNodesToCheck;
        firstNodesToCheck = traverse(cityNode);
        Node bestChildNode = null;

        if (firstNodesToCheck.size() == 1) {
            bestChildNode = firstNodesToCheck.get(0).findNearestNeighbor(cityNode);
        } else if (firstNodesToCheck.size() == 2) {
            bestChildNode = closest(firstNodesToCheck.get(0).findNearestNeighbor(cityNode),
                    firstNodesToCheck.get(1).findNearestNeighbor(cityNode),
                    cityNode);
        }

        if (null != bestChildNode) {
            bestNode = closest(bestChildNode, bestNode, cityNode);
        }

        if (firstNodesToCheck.size() == 1 &&
                null != this.getLeft() &&
                null != this.getRight()) {
            int distance = this.distanceToHyperPlane(cityNode);
            if (distance <= bestNode.distanceTo(cityNode)) {
                Node unchecked;
                if (firstNodesToCheck.get(0).equals(this.getLeft())) {
                    unchecked = this.getRight().findNearestNeighbor(cityNode);
                } else {
                    unchecked = this.getLeft().findNearestNeighbor(cityNode);
                }
                if (unchecked.distanceTo(cityNode) < bestNode.distanceTo(cityNode)) {
                    bestNode = unchecked;
                }
            }
        }

        return bestNode;
    }

    @Override
    public Boolean contains(CityNode cityNode) {
        if (null == this.getCityNode()) {
            return false;
        }
        if (this.getCityNode().equals(cityNode)) {
            return true;
        }
        return this.traverse(cityNode)
                .stream()
                .reduce(false,
                        (partialResult, treeNode) -> partialResult || treeNode.contains(cityNode),
                        (partialResult1, partialResult2) -> partialResult1 || partialResult2);
    }

    @Override
    public List<Node> getChildren() {
        if (isLeafNode()) {
            return new ArrayList<>(Collections.singletonList(this));
        }
        List<Node> children = Stream.of(getLeft(), getRight())
                .map(it -> null != it ? it.getChildren() : null)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        children.add(this);
        return children;
    }

    @Override
    public Integer distanceTo(CityNode cityNode) {
        return Utils.getManhattanDistance(this.cityNode, cityNode);
    }

    private Node getMaxReplacement() {
        List<Node> leftChildren = this.getLeft().getChildren();
        leftChildren.add(this.getLeft());
        leftChildren.sort(nodeComparator);
        return leftChildren.get(leftChildren.size() - 1);
    }

    private Node getReplacement(CityNode cityNode) {
        if (isLeafNode()) {
            return this;
        }
        if (null == left) {
            return getMinReplacement();
        }
        if (null == right) {
            return getMaxReplacement();
        }
        if (dimensionHandler.getDimensionValue(cityNode) < dimensionHandler.getDimensionValue(this.cityNode)) {
            return getMaxReplacement();

        } else {
            return getMinReplacement();
        }
    }

    private Node getMinReplacement() {
        List<Node> rightChildren = this.getRight().getChildren();
        rightChildren.add(this.getRight());
        rightChildren.sort(nodeComparator);
        return rightChildren.get(0);
    }

    private int distanceToHyperPlane(CityNode cityNode) {
        return abs(dimensionHandler.getDimensionValue(cityNode) - dimensionHandler.getDimensionValue(this.cityNode));
    }

    private List<Node> traverse(CityNode cityNode) {
        if (dimensionHandler.getDimensionValue(cityNode) < dimensionHandler.getDimensionValue(cityNode) && null != left) {
            return Collections.singletonList(left);
        } else if (dimensionHandler.getDimensionValue(cityNode) > dimensionHandler.getDimensionValue(cityNode) && null != right) {
            return Collections.singletonList(right);
        } else {
            if (null == right) {
                return null != left ? Collections.singletonList(left) :
                        Collections.emptyList();
            }
            if (null == left) {
                return Collections.singletonList(right);
            }
            return Arrays.asList(left, right);
        }
    }

    private Node closest(Node a, Node b, CityNode cityNode) {
        if (a.distanceTo(cityNode) < b.distanceTo(cityNode)) {
            return a;
        }
        return b;
    }


}
