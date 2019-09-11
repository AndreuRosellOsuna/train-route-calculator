package ro.andreu.recipes.techs.graph.impl;

import jdk.nashorn.internal.runtime.PropertyMap;
import org.junit.Test;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Graph;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.MalformedException;
import ro.andreu.recipes.techs.graph.exception.NoSuchNodeException;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;
import sun.misc.BASE64Decoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;

public class NamedNodeGraphTest {

    @Test
    public void shouldAddANode() {
        NamedNode node = new NamedNode();
        node.setName("A");

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addNode(node);

        Node result = graph.getNode("A");
        assertTrue(node.equals(result));
    }

    @Test
    public void shouldNotAddAnotherNode() {
        NamedNode node = new NamedNode();
        node.setName("A");

        NamedNode sameNode = new NamedNode();
        sameNode.setName("A");

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addNode(node);
        graph.addNode(node);
        graph.addNode(sameNode);

        Node result = graph.getNode("A");
        assertTrue(node.equals(result));
        assertEquals(1, graph.getNodeList().size());
    }

    @Test
    public void shouldAddAnEdge() throws MalformedException {
        NamedNodeEdge ABedge = new NamedNodeEdge();

        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        ABedge.setFrom(nodeA);
        ABedge.setTo(nodeB);
        ABedge.setDistance(new Integer(2));

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(ABedge);


        Node nodeAFromGraph = graph.getNode("A");
        assertTrue(nodeA.equals(nodeAFromGraph));

        Node nodeBFromGraph = graph.getNode("B");
        assertTrue(nodeB.equals(nodeBFromGraph));

        assertEquals(2, graph.getNodeList().size());

        Set<Edge> edgesFromA = nodeAFromGraph.edges();
        assertEquals(1, edgesFromA.size());
        List<Edge> edgesFromAList = edgesFromA.stream().collect(Collectors.toList());
        Node nodeBFromAEdgeList = edgesFromAList.get(0).to();

        assertTrue(nodeB.equals(nodeBFromAEdgeList));

        Set<Edge> edgesFromB = nodeBFromGraph.edges();
        assertEquals(0, edgesFromB.size());


        NamedNodeEdge BAedge = new NamedNodeEdge();
        BAedge.setFrom(nodeB);
        BAedge.setTo(nodeA);
        BAedge.setDistance(3);
        graph.addEdge(BAedge);

        nodeBFromGraph = graph.getNode("B");
        edgesFromB = nodeBFromGraph.edges();
        assertEquals(1, edgesFromB.size());

        List<Edge> edgesFromBList = edgesFromB.stream().collect(Collectors.toList());
        Node nodeAFromBEdgeList = edgesFromBList.get(0).to();

        assertTrue(nodeA.equals(nodeAFromBEdgeList));
    }

    @Test
    public void shouldFindRouteAToB() throws MalformedException, NoSuchNodeException, NoSuchRouteException {
        NamedNodeEdge ABedge = new NamedNodeEdge();

        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        ABedge.setFrom(nodeA);
        ABedge.setTo(nodeB);
        ABedge.setDistance(new Integer(2));

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(ABedge);

        Set<Route> routes = graph.getAllRoutes("A", "B");
        assertEquals(1, routes.size());

        Route ABRoute = routes.iterator().next();

        assertTrue(graph.shortestRouteBetweenNodes("A", "B").equals(ABRoute));

        assertThat(ABRoute.nodes()).extracting("name").contains("A", "B");

        Number distance = graph.distanceAlongRoute(ABRoute);
        assertEquals(2, distance.intValue());
    }

    @Test
    public void shouldFindRouteAToCByB() throws MalformedException, NoSuchNodeException, NoSuchRouteException {
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

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(edgeAB);
        graph.addEdge(edgeBC);


        Set<Route> routes = graph.getAllRoutes("A", "C");
        assertEquals(1, routes.size());

        Route routeAC = routes.iterator().next();

        assertTrue(graph.shortestRouteBetweenNodes("A", "C").equals(routeAC));

        assertThat(routeAC.nodes()).extracting("name").containsExactly("A", "B", "C");

        Number distance = graph.distanceAlongRoute(routeAC);
        assertEquals(3, distance.intValue());
    }

    @Test
    public void shouldFindRouteAToDByBAndC() throws MalformedException, NoSuchNodeException, NoSuchRouteException {
        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        NamedNode nodeC = new NamedNode();
        nodeC.setName("C");

        NamedNode nodeD = new NamedNode();
        nodeD.setName("D");

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

        NamedNodeEdge edgeCD = new NamedNodeEdge();
        edgeCD.setFrom(nodeC);
        edgeCD.setTo(nodeD);
        edgeCD.setDistance(new Integer(3));

        nodeC.addEdge(edgeCD);

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(edgeAB);
        graph.addEdge(edgeBC);
        graph.addEdge(edgeCD);

        Set<Route> routes = graph.getAllRoutes("A", "D");
        assertEquals(1, routes.size());

        Route routeAD = routes.iterator().next();

        assertTrue(graph.shortestRouteBetweenNodes("A", "D").equals(routeAD));

        assertThat(routeAD.nodes()).extracting("name").containsExactly("A", "B", "C", "D");

        Number distance = graph.distanceAlongRoute(routeAD);
        assertEquals(6, distance.intValue());
    }

    @Test(expected = NoSuchRouteException.class)
    public void shouldNotFindAnyRoute() throws NoSuchNodeException, NoSuchRouteException, MalformedException {
        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        NamedNode nodeC = new NamedNode();
        nodeC.setName("C");

        NamedNode nodeD = new NamedNode();
        nodeD.setName("D");

        NamedNodeEdge edgeAB = new NamedNodeEdge();
        edgeAB.setFrom(nodeA);
        edgeAB.setTo(nodeB);
        edgeAB.setDistance(new Integer(1));

        nodeA.addEdge(edgeAB);

        NamedNodeEdge edgeBC = new NamedNodeEdge();
        edgeBC.setFrom(nodeB);
        edgeBC.setTo(nodeC);
        edgeBC.setDistance(new Integer(2));

//        nodeB.addEdge(edgeBC);

        NamedNodeEdge edgeCD = new NamedNodeEdge();
        edgeCD.setFrom(nodeC);
        edgeCD.setTo(nodeD);
        edgeCD.setDistance(new Integer(3));

        nodeC.addEdge(edgeCD);

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(edgeAB);
//        graph.addEdge(edgeBC);
        graph.addEdge(edgeCD);

        Set<Route> routes = graph.getAllRoutes("A", "D");
    }

    @Test
    public void shouldFindRouteACDInsteadABD() throws MalformedException, NoSuchNodeException, NoSuchRouteException {
        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        NamedNode nodeC = new NamedNode();
        nodeC.setName("C");

        NamedNode nodeD = new NamedNode();
        nodeD.setName("D");

        NamedNodeEdge edgeAB = new NamedNodeEdge();
        edgeAB.setFrom(nodeA);
        edgeAB.setTo(nodeB);
        edgeAB.setDistance(new Integer(10));

        nodeA.addEdge(edgeAB);

        NamedNodeEdge edgeAC = new NamedNodeEdge();
        edgeAC.setFrom(nodeA);
        edgeAC.setTo(nodeC);
        edgeAC.setDistance(new Integer(3));

        nodeA.addEdge(edgeAC);

        NamedNodeEdge edgeBC = new NamedNodeEdge();
        edgeBC.setFrom(nodeB);
        edgeBC.setTo(nodeC);
        edgeBC.setDistance(new Integer(20));

        nodeB.addEdge(edgeBC);

        NamedNodeEdge edgeCD = new NamedNodeEdge();
        edgeCD.setFrom(nodeC);
        edgeCD.setTo(nodeD);
        edgeCD.setDistance(new Integer(2));

        nodeC.addEdge(edgeCD);

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(edgeAB);
        graph.addEdge(edgeAC);
        graph.addEdge(edgeBC);
        graph.addEdge(edgeCD);

        Set<Route> routes = graph.getAllRoutes("A", "D");
        assertEquals(2, routes.size());

        Route shortestRoute = graph.shortestRouteBetweenNodes("A", "D");

        assertThat(shortestRoute.nodes()).extracting("name").containsExactly("A", "C", "D");

        Number distance = graph.distanceAlongRoute(shortestRoute);
        assertEquals(5, distance.intValue());
    }

    @Test
    public void shouldNotFindARouteWithoutRepeatingNodes() throws NoSuchNodeException, NoSuchRouteException, MalformedException {
        NamedNode nodeA = new NamedNode();
        nodeA.setName("A");

        NamedNode nodeB = new NamedNode();
        nodeB.setName("B");

        NamedNode nodeC = new NamedNode();
        nodeC.setName("C");

        NamedNode nodeD = new NamedNode();
        nodeD.setName("D");

        NamedNodeEdge edgeAD = new NamedNodeEdge();
        edgeAD.setFrom(nodeA);
        edgeAD.setTo(nodeD);
        edgeAD.setDistance(new Integer(100));

        nodeA.addEdge(edgeAD);

        NamedNodeEdge edgeAB = new NamedNodeEdge();
        edgeAB.setFrom(nodeA);
        edgeAB.setTo(nodeB);
        edgeAB.setDistance(new Integer(1));

        nodeA.addEdge(edgeAB);

        NamedNodeEdge edgeAC = new NamedNodeEdge();
        edgeAC.setFrom(nodeA);
        edgeAC.setTo(nodeC);
        edgeAC.setDistance(new Integer(10));

        nodeA.addEdge(edgeAC);

        NamedNodeEdge edgeCA = new NamedNodeEdge();
        edgeCA.setFrom(nodeC);
        edgeCA.setTo(nodeA);
        edgeCA.setDistance(new Integer(10));

        nodeA.addEdge(edgeCA);

        NamedNodeEdge edgeBC = new NamedNodeEdge();
        edgeBC.setFrom(nodeB);
        edgeBC.setTo(nodeC);
        edgeBC.setDistance(new Integer(1));

        nodeB.addEdge(edgeBC);

        NamedNodeEdge edgeCD = new NamedNodeEdge();
        edgeCD.setFrom(nodeC);
        edgeCD.setTo(nodeD);
        edgeCD.setDistance(new Integer(1));

        nodeC.addEdge(edgeCD);

        NamedNodeGraph graph = new NamedNodeGraph();
        graph.addEdge(edgeAB);
        graph.addEdge(edgeAC);
        graph.addEdge(edgeCA);
        graph.addEdge(edgeBC);
        graph.addEdge(edgeCD);

        Set<Route> routes = graph.getAllRoutes("A", "D");
        assertEquals(3, routes.size());

        Route shortestRoute = graph.shortestRouteBetweenNodes("A", "D");

        assertThat(shortestRoute.nodes()).extracting("name").containsExactly("A", "B", "C", "D");

        Number distance = graph.distanceAlongRoute(shortestRoute);
        assertEquals(3, distance.intValue());

        int differentRoutes = graph.differentRoutesBetweenNodes("A", "D");
        assertEquals(3, differentRoutes);
    }
}
