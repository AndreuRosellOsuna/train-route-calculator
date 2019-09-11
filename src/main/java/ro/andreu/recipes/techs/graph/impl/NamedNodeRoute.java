package ro.andreu.recipes.techs.graph.impl;

import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import java.util.ArrayList;
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

            // Verify that we can go to this new node from last node throw one of its edges
            Set<Edge> edgeFromLastNodeToNewNodeSet = nodes.get(nodes.size()-1).edges().stream().filter(edge -> edge.to().equals(node)).collect(Collectors.toSet());
            if(edgeFromLastNodeToNewNodeSet.isEmpty()) {
                String desc = "The last node " + nodes.get(nodes.size()-1).toString() +" of this route does not have an edge to the new added node " + node.toString();
                throw new NoSuchRouteException(desc);
            }

            Edge edge = edgeFromLastNodeToNewNodeSet.iterator().next();
            edges.add(edge);
        }

        nodes.add(node);
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
