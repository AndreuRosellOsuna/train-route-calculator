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

    public int sortMethod(Route route) {
        return distanceAlongRoute(route).intValue();
    }

    @Override
    public Route shortestRouteBetweenNodes(String from, String to) throws NoSuchNodeException, NoSuchRouteException {
        Set<Route> allRoutes = this.getAllRoutes(from, to);
        List<Route> sortedRoutes = new ArrayList<>(allRoutes).stream().sorted((routeA, routeB) -> {
            return this.distanceAlongRoute(routeA).intValue() - this.distanceAlongRoute(routeB).intValue();
        }).collect(Collectors.toList());

        return sortedRoutes.get(0);
    }

    public Set<Route> getAllRoutes(String from, String to) throws NoSuchNodeException, NoSuchRouteException {
        Node fromNode = nodes.get(from);
        if(Objects.isNull(fromNode)) {
            throw new NoSuchNodeException();
        }

        Node toNode = nodes.get(to);
        if(Objects.isNull(toNode)) {
            throw new NoSuchNodeException();
        }

        if(fromNode.equals(toNode)) {
            throw new NoSuchRouteException();
        }

        Set<Route> allPossibleRoutes = new HashSet<>();
        Route route = new NamedNodeRoute();

        findNode(fromNode, toNode, route, allPossibleRoutes);

        if(allPossibleRoutes.size() == 0)
            throw new NoSuchRouteException();

        return allPossibleRoutes;
    }

    private void findNode(Node fromNode, Node toNode, Route actualRoute, Set<Route> allPossibleRoutes) throws NoSuchRouteException {
        actualRoute.addNode(fromNode);

//        if(fromNode.neighbours().contains(toNode)) {
        if(toNode.equals(fromNode)) {
            allPossibleRoutes.add(actualRoute);
        } else {

            for(Edge edge : fromNode.edges()) {
                if(!edge.to().equals(fromNode) && !actualRoute.nodes().contains(edge.to())) {
                    Route newRoute = new NamedNodeRoute(actualRoute.nodes(), actualRoute.edges());
//                    newRoute.addEdge(edge);
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
    public List<NamedNodeEdge> getEdgeList() {
        return null;
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
            throw new MalformedException();
        }

        NamedNode from = edge.from();
        addNode(from);
        from.addEdge(edge);

        NamedNode to = edge.to();
        addNode(to);
    }
}
