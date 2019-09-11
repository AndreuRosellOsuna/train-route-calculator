package ro.andreu.recipes.techs.graph.impl;

import org.springframework.util.StringUtils;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Graph;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.MalformedException;
import ro.andreu.recipes.techs.graph.exception.NoSuchNodeException;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import java.util.*;
import java.util.stream.Collectors;

public class NamedNodeGraph implements Graph<String, NamedNode, NamedNodeEdge> {

    private Map<String, NamedNode> nodes;

    public NamedNodeGraph() {
        nodes = new HashMap<String, NamedNode>();
    }

    @Override
    public Number distanceAlongRoute(Route route) {
        Float distance = route.edges().stream().map(Edge::distance).map(Number::floatValue).reduce(0f, (subtotal, element) -> subtotal + element);
        return distance;
    }

    @Override
    public int differentRoutesBetweenNodes(String from, String to) throws NoSuchNodeException, NoSuchRouteException {
        Set<Route> allRoutes = this.getAllRoutes(from, to);
        return allRoutes.size();
    }

    @Override
    public Route shortestRouteBetweenNodes(String from, String to) throws NoSuchNodeException, NoSuchRouteException {
        Set<Route> allRoutes = this.getAllRoutes(from, to);
        List<Route> sortedRoutes = new ArrayList<>(allRoutes).stream().sorted((routeA, routeB) -> {
            return this.distanceAlongRoute(routeA).intValue() - this.distanceAlongRoute(routeB).intValue();
        }).collect(Collectors.toList());

        return sortedRoutes.get(0);
    }

    @Override
    public Route validateRoute(String... nodes) throws NoSuchNodeException, NoSuchRouteException {
        NamedNodeRoute route = new NamedNodeRoute();

        for(String nodeName : nodes) {
            Node node = getNode(nodeName);
            if(node != null) {
                route.addNode(node);
            } else {
                throw new NoSuchNodeException("Node " + nodeName + " is not in the graph");
            }
        }

        return route;
    }


    public Set<Route> getAllRoutes(String from, String to) throws NoSuchNodeException, NoSuchRouteException {
        Node fromNode = nodes.get(from);
        if(Objects.isNull(fromNode)) {
            throw new NoSuchNodeException("Node " + from + " is not in the graph");
        }

        Node toNode = nodes.get(to);
        if(Objects.isNull(toNode)) {
            throw new NoSuchNodeException("Node " + to + " is not in the graph");
        }

        if(fromNode.equals(toNode)) {
            throw new NoSuchRouteException("Nodes " + from + " and " + to + " are equal");
        }

        Set<Route> allPossibleRoutes = new HashSet<>();
        Route route = new NamedNodeRoute();

        findNode(fromNode, toNode, route, allPossibleRoutes);

        if(allPossibleRoutes.size() == 0)
            throw new NoSuchRouteException("No such route for " + from + " and " + to + " nodes");

        return allPossibleRoutes;
    }

    private void findNode(Node fromNode, Node toNode, Route actualRoute, Set<Route> allPossibleRoutes) throws NoSuchRouteException {
        actualRoute.addNode(fromNode);

        if(toNode.equals(fromNode)) {
            allPossibleRoutes.add(actualRoute);
        } else {
            for(Edge edge : fromNode.edges()) {
                if(!edge.to().equals(fromNode) && !actualRoute.nodes().contains(edge.to())) {
                    Route newRoute = new NamedNodeRoute(actualRoute.nodes(), actualRoute.edges());
                    Node toNodeFromEdge = edge.to();
                    findNode(toNodeFromEdge, toNode, newRoute, allPossibleRoutes);
                }
            }
        }
    }

    @Override
    public List<NamedNode> getNodeList() {
        return new ArrayList<NamedNode>(nodes.values());
    }

    @Override
    public Node getNode(String key) {
        return nodes.get(key);
    }

    @Override
    public void addNode(NamedNode node) {
        String name = node.getName();
        Node graphNode = nodes.get(name);

        if(!Objects.nonNull(graphNode) && !StringUtils.isEmpty(name)) {
            nodes.put(name, node);
        }
    }

    @Override
    public void addEdge(NamedNodeEdge edge) throws MalformedException {
        if(Objects.isNull(edge.from()) || Objects.isNull(edge.to()) || Objects.isNull(edge.distance())) {
            throw new MalformedException("Edge not completed : from " + edge.from() + " to " + edge.to() + " distance " + edge.distance());
        }

        NamedNode from = edge.from();
        addNode(from);
        NamedNode fromNode = nodes.get(from.getName());

        NamedNode to = edge.to();
        addNode(to);
        NamedNode toNode = nodes.get(to.getName());

        edge.setFrom(fromNode);
        edge.setTo(toNode);
        fromNode.addEdge(edge);
    }
}
