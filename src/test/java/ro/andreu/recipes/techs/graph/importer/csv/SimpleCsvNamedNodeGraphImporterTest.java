package ro.andreu.recipes.techs.graph.importer.csv;

import org.junit.Test;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.impl.NamedNode;
import ro.andreu.recipes.techs.graph.impl.NamedNodeGraph;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.Assert.*;

public class SimpleCsvNamedNodeGraphImporterTest {

    @Test
    public void importTest() throws FileNotFoundException {
        SimpleCsvNamedNodeGraphImporter importer = new SimpleCsvNamedNodeGraphImporter();
        SimpleCsvNamedNodeGraphImporterResource railroadFile = new SimpleCsvNamedNodeGraphImporterResource("D:\\java_projects\\Train Route Calculator\\src\\test\\resources\\railroad_file_test.csv");
        NamedNodeGraph graph = importer.importGraph(railroadFile);

        NamedNode nodeA = (NamedNode) graph.getNode("A");
        NamedNode nodeB = (NamedNode) graph.getNode("B");
        NamedNode nodeC = (NamedNode) graph.getNode("C");
        NamedNode nodeD = (NamedNode) graph.getNode("D");

        NamedNode nodeX = (NamedNode) graph.getNode("X");
        NamedNode nodeY = (NamedNode) graph.getNode("Y");
        NamedNode nodeZ = (NamedNode) graph.getNode("Z");
        NamedNode nodeW = (NamedNode) graph.getNode("W");

        assertEquals("A", nodeA.getName());

        Set<Node> nodeANeighbours = nodeA.neighbours();
        assertTrue(nodeANeighbours.contains(nodeB));
        assertTrue(nodeANeighbours.contains(nodeC));
        assertTrue(nodeANeighbours.contains(nodeD));

        assertEquals("B", nodeB.getName());

        Set<Node> nodeBNeighbours = nodeB.neighbours();
        assertTrue(nodeBNeighbours.contains(nodeC));
        assertFalse(nodeBNeighbours.contains(nodeD));
        assertFalse(nodeBNeighbours.contains(nodeA));

        assertEquals("C", nodeC.getName());

        Set<Node> nodeCNeighbours = nodeC.neighbours();
        assertFalse(nodeCNeighbours.contains(nodeB));
        assertFalse(nodeCNeighbours.contains(nodeD));
        assertFalse(nodeCNeighbours.contains(nodeA));

        assertEquals("D", nodeD.getName());

        Set<Node> nodeDNeighbours = nodeD.neighbours();
        assertFalse(nodeDNeighbours.contains(nodeB));
        assertFalse(nodeDNeighbours.contains(nodeC));
        assertTrue(nodeDNeighbours.contains(nodeA));

        assertEquals("X", nodeX.getName());

        Set<Node> nodeXNeighbours = nodeX.neighbours();
        assertTrue(nodeXNeighbours.contains(nodeY));
        assertTrue(nodeXNeighbours.contains(nodeZ));
        assertTrue(nodeXNeighbours.contains(nodeW));

        assertEquals("Y", nodeY.getName());

        Set<Node> nodeYNeighbours = nodeY.neighbours();
        assertFalse(nodeYNeighbours.contains(nodeX));
        assertTrue(nodeYNeighbours.contains(nodeZ));
        assertFalse(nodeYNeighbours.contains(nodeW));
    }
}
