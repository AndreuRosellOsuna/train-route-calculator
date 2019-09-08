package ro.andreu.recipes.techs.graph.impl;

import org.springframework.util.StringUtils;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Graph;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.NoSuchNodeException;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import java.util.*;

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
        List<Route> sortedRoutes = new ArrayList<>(allRoutes).sort(this::sortMethod);
    }

    @Override
    public List<NamedNodeEdge> getNodeList() {
        return null;
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

        if(Objects.nonNull(graphNode) && !StringUtils.isEmpty(name)) {
            nodes.put(name, node);
        }
    }

    @Override
    public void addEdge(NamedNodeEdge edge) {
        if(Objects.isNull(edge.from()) || Objects.isNull(edge.to()) || Objects.isNull(edge.distance())) {
            // TODO add a new exception
            return ;
        }

        NamedNode from = edge.from();
        addNode(from);
        from.addEdge(edge);

        NamedNode to = edge.to();
        addNode(to);


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

        Set<Route> allRoutes = new HashSet<>();

        Route route = new NamedNodeRoute();

        findNode(fromNode, toNode, route, allRoutes);

        return allRoutes;
    }

    private void findNode(Node fromNode, Node toNode, Route actualRoute, Set<Route> allRoutes) {
        actualRoute.addNode(fromNode);

        if(fromNode.neighbours().contains(toNode)) {
            actualRoute.addNode(toNode);
            allRoutes.add(actualRoute);
        } else {

            Set<Edge> edges = fromNode.edges();

            for(Edge edge : edges) {
                Route newRoute = new NamedNodeRoute(actualRoute.nodes());
                Node toNodeFromEdge = edge.to();
                findNode(toNodeFromEdge, toNode, newRoute, allRoutes);
            }
        }
    }
}
