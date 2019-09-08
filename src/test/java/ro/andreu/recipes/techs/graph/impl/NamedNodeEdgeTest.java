package ro.andreu.recipes.techs.graph.impl;

import org.junit.Test;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class NamedNodeEdgeTest {

    @Test
    public void shouldBeEqualsTest() {
        NamedNode nodeA1 = new NamedNode();
        nodeA1.setName("A");

        NamedNode nodeB1 = new NamedNode();
        nodeB1.setName("B");

        NamedNodeEdge edge1 = new NamedNodeEdge();
        edge1.setFrom(nodeA1);
        edge1.setTo(nodeB1);
        edge1.setDistance(new Integer(1));

        NamedNode nodeA2 = new NamedNode();
        nodeA2.setName("A");

        NamedNode nodeB2 = new NamedNode();
        nodeB2.setName("B");

        NamedNodeEdge edge2 = new NamedNodeEdge();
        edge2.setFrom(nodeA2);
        edge2.setTo(nodeB2);
        edge2.setDistance(new Integer(1));

        assertTrue(edge1.equals(edge2));
    }

    @Test
    public void shouldNotBeEqualsTest() {
        NamedNode nodeA1 = new NamedNode();
        nodeA1.setName("A");

        NamedNode nodeB1 = new NamedNode();
        nodeB1.setName("B");

        NamedNodeEdge edge1 = new NamedNodeEdge();
        edge1.setFrom(nodeA1);
        edge1.setTo(nodeB1);
        edge1.setDistance(new Integer(1));

        NamedNode nodeA2 = new NamedNode();
        nodeA2.setName("A");

        NamedNode nodeB2 = new NamedNode();
        nodeB2.setName("B");

        NamedNodeEdge edge2 = new NamedNodeEdge();
        edge2.setFrom(nodeB2);
        edge2.setTo(nodeA2);
        edge2.setDistance(new Integer(1));

        assertFalse(edge1.equals(edge2));

        NamedNodeEdge edge3 = new NamedNodeEdge();
        edge3.setFrom(nodeA2);
        edge3.setTo(nodeB2);
        edge3.setDistance(new Integer(2));

        assertFalse(edge1.equals(edge3));
    }
}
