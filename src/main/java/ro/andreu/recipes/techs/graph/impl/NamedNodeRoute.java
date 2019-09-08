package ro.andreu.recipes.techs.graph.impl;

import org.springframework.util.StringUtils;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NamedNodeRoute implements Route {

    private List<Node> nodes;

    private List<Edge> edges;

    public NamedNodeRoute(List<Node> nodes, List<Edge> edges) {
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>(edges);
    }

    public NamedNodeRoute() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public List<Node> nodes() {
        return nodes;
    }

    @Override
    public List<Edge> edges() { return this.edges; }

    @Override
    public void addNode(Node node) throws NoSuchRouteException {
        if(!nodes.isEmpty()) {

            NamedNodeEdge newEdge = new NamedNodeEdge();
//            Set<Node> fromNodeSet = node.edges().stream().map(Edge::to).filter(toNode -> toNode.equals(node)).collect(Collectors.toSet());
            Set<Edge> edgeFromLastNodeToNewNodeSet = nodes.get(nodes.size()-1).edges().stream().filter(edge -> edge.to().equals(node)).collect(Collectors.toSet());
            if(edgeFromLastNodeToNewNodeSet.isEmpty()) {
                throw new NoSuchRouteException();
            }

            Edge edge = edgeFromLastNodeToNewNodeSet.iterator().next();
            edges.add(edge);
        }

        nodes.add(node);
    }

//    @Override
    private void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public int size() {
        return nodes().size();
    }

    @Override
    public Node actual() {
        return null;
    }

    @Override
    public boolean isNext() {
        return false;
    }

    @Override
    public Node next() {
        return null;
    }

    @Override
    public Node first() {
        return null;
    }

    @Override
    public boolean equals(Object obj){

        if (obj instanceof NamedNodeRoute) {
            NamedNodeRoute route = (NamedNodeRoute) obj;

            if(route.nodes().size() != this.nodes.size()) {
                return false;
            }

            boolean allNodesEquals = true;
            for(int i=0; i < route.nodes().size(); i++) {
                boolean nodesEqualsInIndex = route.nodes().get(i).equals(this.nodes.get(i));
                allNodesEquals = allNodesEquals && nodesEqualsInIndex;
            }

            boolean allEdgesEquals = true;
            for(int i=0; i < route.edges().size(); i++) {
                boolean edgeEqualsInIndex = route.edges().get(i).equals(this.edges.get(i));
                allEdgesEquals = allEdgesEquals && edgeEqualsInIndex;
            }

            return allNodesEquals && allEdgesEquals;
        }
        return false;
    }

    @Override
    public String toString() {
        return nodes.stream().map(Node::toString).map(name -> name + " ").collect(Collectors.joining());
    }
}
