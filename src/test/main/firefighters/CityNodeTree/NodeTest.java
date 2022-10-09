package main.firefighters.CityNodeTree;

import main.api.CityNode;
import main.firefighters.CityNodeTree.api.Node;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void testEmptyCityNodes() {
        XNode xNode = new XNode(Collections.emptyList(), 0, null);
        assertNotNull(xNode);
        assertNull(xNode.getCityNode());
        assertNull(xNode.getLeft());
        assertNull(xNode.getRight());

        YNode yNode = new YNode(Collections.emptyList(), 1, null);
        assertNotNull(yNode);
        assertNull(yNode.getCityNode());
        assertNull(yNode.getLeft());
        assertNull(yNode.getRight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDepthX() {
        XNode node = new XNode(Collections.emptyList(), 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDepthY() {
        YNode node = new YNode(Collections.emptyList(), 0, null);
    }

    @Test
    public void testCreate() {
        List<CityNode> cityNodes = Arrays.asList(
                new CityNode(8, 7),
                new CityNode(5, 4),
                new CityNode(2, 6),
                new CityNode(13, 4),
                new CityNode(3, 1),
                new CityNode(9, 3),
                new CityNode(11, 6)
        );

        XNode xnode = new XNode(cityNodes, 0, null);
        YNode ynode = new YNode(cityNodes, 1, null);

        List<Node> xChildren = xnode.getChildren();
        List<Node> yChildren = ynode.getChildren();
        assertEquals(cityNodes.size(), xChildren.size());
        assertEquals(cityNodes.size(), yChildren.size());

        for (Node node : xChildren) {
            assertEquals(1, cityNodes.stream().filter(it -> node.distanceTo(it) == 0).count());
        }

        for (Node node : yChildren) {
            assertEquals(1, cityNodes.stream().filter(it -> node.distanceTo(it) == 0).count());
        }

        assertEquals(1,xChildren.stream().filter(it -> it.isRoot()).count());
        assertEquals(1,yChildren.stream().filter(it -> it.isRoot()).count());


    }

}