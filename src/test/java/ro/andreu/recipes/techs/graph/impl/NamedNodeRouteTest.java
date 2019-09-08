package ro.andreu.recipes.techs.graph.impl;

import org.junit.Test;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import static org.junit.Assert.*;

public class NamedNodeRouteTest {

    public NamedNodeRoute createRouteABC() throws NoSuchRouteException {
        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        NamedNode nodeC = new NamedNode();
        nodeC.setName("C");

        NamedNodeEdge edgeAB = new NamedNodeEdge();
        edgeAB.setFrom(nodeA);
        edgeAB.setTo(nodeB);
        edgeAB.setDistance(new Integer(1));

        nodeA.addEdge(edgeAB);

        NamedNodeEdge edgeBC = new NamedNodeEdge();
        edgeBC.setFrom(nodeB);
        edgeBC.setTo(nodeC);
        edgeBC.setDistance(new Integer(2));

        nodeB.addEdge(edgeBC);

        NamedNodeEdge edgeAC = new NamedNodeEdge();
        edgeAC.setFrom(nodeA);
        edgeAC.setTo(nodeC);
        edgeAC.setDistance(new Integer(4));

        nodeA.addEdge(edgeAC);

        NamedNodeEdge edgeCB = new NamedNodeEdge();
        edgeCB.setFrom(nodeC);
        edgeCB.setTo(nodeB);
        edgeCB.setDistance(new Integer(5));

        nodeC.addEdge(edgeCB);

        NamedNodeRoute route1 = new NamedNodeRoute();
        route1.addNode(nodeA);
        route1.addNode(nodeB);
        route1.addNode(nodeC);

        return route1;
    }

    public NamedNodeRoute createRouteACB() throws NoSuchRouteException {
        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        NamedNode nodeC = new NamedNode();
        nodeC.setName("C");

        NamedNodeEdge edgeAB = new NamedNodeEdge();
        edgeAB.setFrom(nodeA);
        edgeAB.setTo(nodeB);
        edgeAB.setDistance(new Integer(1));

        nodeA.addEdge(edgeAB);

        NamedNodeEdge edgeBC = new NamedNodeEdge();
        edgeBC.setFrom(nodeB);
        edgeBC.setTo(nodeC);
        edgeBC.setDistance(new Integer(2));

        nodeB.addEdge(edgeBC);

        NamedNodeEdge edgeAC = new NamedNodeEdge();
        edgeAC.setFrom(nodeA);
        edgeAC.setTo(nodeC);
        edgeAC.setDistance(new Integer(4));

        nodeA.addEdge(edgeAC);

        NamedNodeEdge edgeCB = new NamedNodeEdge();
        edgeCB.setFrom(nodeC);
        edgeCB.setTo(nodeB);
        edgeCB.setDistance(new Integer(5));

        nodeC.addEdge(edgeCB);

        NamedNodeRoute route2 = new NamedNodeRoute();
        route2.addNode(nodeA);
        route2.addNode(nodeC);
        route2.addNode(nodeB);

        return route2;
    }

    @Test
    public void shouldBeEqualsTest() throws NoSuchRouteException {
        NamedNodeRoute route1 = createRouteABC();
        NamedNodeRoute route2 = createRouteABC();

        assertEquals(route1, route2);
    }

    @Test
    public void shouldNotBeEqualsTest() throws NoSuchRouteException {
        NamedNodeRoute route1 = createRouteABC();
        NamedNodeRoute route2 = createRouteACB();

        assertFalse(route1.equals(route2));
    }
}
