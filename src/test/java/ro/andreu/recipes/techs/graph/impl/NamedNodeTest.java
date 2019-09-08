package ro.andreu.recipes.techs.graph.impl;

import org.junit.Test;
import ro.andreu.recipes.techs.graph.Node;

import static org.junit.Assert.*;

public class NamedNodeTest {

    @Test
    public void equalsTest() {
        NamedNode node1 = new NamedNode();
        node1.setName("A");
        NamedNode node2 = new NamedNode();
        node2.setName("A");

        assertTrue(node1.equals(node2));
    }
    @Test
    public void notEqualsTest() {
        NamedNode node1 = new NamedNode();
        node1.setName("A");
        NamedNode node2 = new NamedNode();
        node2.setName("B");

        assertFalse(node1.equals(node2));
    }

    @Test
    public void equalsInterfaceTest() {
        NamedNode node1 = new NamedNode();
        node1.setName("A");
        NamedNode node2 = new NamedNode();
        node2.setName("A");

        Node nodeInterface1 = (Node) node1;
        Node nodeInterface2 = (Node) node2;

        assertTrue(nodeInterface1.equals(nodeInterface2));
    }

    @Test
    public void notEqualsInterfaceTest() {
        NamedNode node1 = new NamedNode();
        node1.setName("A");
        NamedNode node2 = new NamedNode();
        node2.setName("B");

        Node nodeInterface1 = (Node) node1;
        Node nodeInterface2 = (Node) node2;

        assertFalse(nodeInterface1.equals(nodeInterface2));
    }
}
