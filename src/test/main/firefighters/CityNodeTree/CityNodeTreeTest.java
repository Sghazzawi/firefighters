package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.Tree;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CityNodeTreeTest {

    @Test
    public void testCreateTree() {
        List<CityNode> cityNodes = Arrays.asList(
                new CityNode(2, 0),
                new CityNode(3, 1),
                new CityNode(3, 5),
                new CityNode(4, 6),

                new CityNode(0, 2),
                new CityNode(1, 3),
                new CityNode(5, 3),
                new CityNode(6, 4));

        Tree cityNodeTree = new CityNodeTree(cityNodes);
        assertEquals(cityNodes.size(), cityNodeTree.size());
        for (CityNode cityNode : cityNodes) {
            assertTrue(cityNodeTree.contains(cityNode));
        }
        assertFalse(cityNodes.contains(new CityNode(3,3)));
    }

    @Test
    public void testCreateTreeOneNode() {
        List<CityNode> cityNodes = Collections.singletonList(new CityNode(2, 0));

        Tree cityNodeTree = new CityNodeTree(cityNodes);
        assertEquals(cityNodes.size(), cityNodeTree.size());
        for (CityNode cityNode : cityNodes) {
            assertTrue(cityNodeTree.contains(cityNode));
        }
        assertFalse(cityNodes.contains(new CityNode(3,3)));
    }

    @Test
    public void testFindNearestNeighbor() {
        List<CityNode> cityNodes = Arrays.asList(
                new CityNode(2, 0),
                new CityNode(3, 1),
                new CityNode(3, 5),
                new CityNode(4, 6),

                new CityNode(0, 2),
                new CityNode(1, 3),
                new CityNode(5, 3),
                new CityNode(6, 4));

        Tree cityNodeTree = new CityNodeTree(cityNodes);
        CityNode nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(0, 1));
        assertEquals(new CityNode(0, 2), nearestNeighbor);
        nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(6, 6));
        assertEquals(new CityNode(6, 4), nearestNeighbor);
        nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(5, 3));
        assertEquals(new CityNode(5, 3), nearestNeighbor);
    }

    @Test
    public void testFindNearestNeighborOneNode() {
        List<CityNode> cityNodes = Collections.singletonList(
                new CityNode(2, 0));

        Tree cityNodeTree = new CityNodeTree(cityNodes);
        CityNode nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(0, 1));
        assertEquals(new CityNode(2, 0), nearestNeighbor);
        nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(6, 6));
        assertEquals(new CityNode(2, 0), nearestNeighbor);
        nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(5, 3));
        assertEquals(new CityNode(2, 0), nearestNeighbor);
    }

    @Test
    public void findNearestNeighbor2() {
        List<CityNode> cityNodes = Arrays.asList(
                new CityNode(8, 7),
                new CityNode(5, 4),
                new CityNode(2, 6),
                new CityNode(13, 4),
                new CityNode(3, 1),
                new CityNode(9, 3),
                new CityNode(11, 6)
        );
        Tree cityNodeTree = new CityNodeTree(cityNodes);
        CityNode nearestNeighbor = cityNodeTree.findNearestNeighbor(new CityNode(9, 5));
        assertEquals(new CityNode(9,3),nearestNeighbor);
    }

    @Test
    public void testRemoveTreeOneNode() {
        CityNode cityNode = new CityNode(2, 0);
        List<CityNode> cityNodes = Collections.singletonList(cityNode);
        Tree cityNodeTree = new CityNodeTree(cityNodes);
        cityNodeTree.remove(cityNode);
        assertEquals(0, cityNodeTree.size());
        assertFalse(cityNodeTree.contains(cityNode));
    }

    @Test
    public void testRemoveLeafNode() {
        CityNode cityNode = new CityNode(1, 1);
        List<CityNode> cityNodes = Arrays.asList(cityNode, new CityNode(2, 2), new CityNode(3, 3));
        Tree cityNodeTree = new CityNodeTree(cityNodes);
        cityNodeTree.remove(cityNode);
        assertEquals(2, cityNodeTree.size());
        assertFalse(cityNodeTree.contains(cityNode));
    }

    @Test
    public void testRemoveNode() {
        CityNode cityNode = new CityNode(2, 2);
        List<CityNode> cityNodes = Arrays.asList(cityNode,
                new CityNode(1, 1),
                new CityNode(3, 3),
                new CityNode(4, 4));
        CityNodeTree cityNodeTree = new CityNodeTree(cityNodes);
        cityNodeTree.remove(cityNode);
        assertEquals(3, cityNodeTree.size());
        assertFalse(cityNodeTree.contains(cityNode));
    }

    @Test
    public void testRemoveRootNode() {
        CityNode cityNode = new CityNode(3, 3);
        List<CityNode> cityNodes = Arrays.asList(cityNode,
                new CityNode(1, 1),
                new CityNode(2, 2),
                new CityNode(4, 4));
        CityNodeTree cityNodeTree = new CityNodeTree(cityNodes);
        cityNodeTree.remove(cityNode);
        assertEquals(3, cityNodeTree.size());
        assertFalse(cityNodeTree.contains(cityNode));
    }
}
