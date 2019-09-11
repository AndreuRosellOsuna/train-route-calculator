package ro.andreu.recipes.techs.graph;

import ro.andreu.recipes.techs.graph.exception.MalformedException;
import ro.andreu.recipes.techs.graph.exception.NoSuchNodeException;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;

import java.util.List;

public interface Graph<K, N extends Node, E extends Edge> {

    public List<N> getNodeList();

    public Node getNode(K key) throws NoSuchNodeException;

    public void addNode(N node);

    public void addEdge(E edge) throws MalformedException;

    public Number distanceAlongRoute(Route route) throws NoSuchRouteException;

    public int differentRoutesBetweenNodes(K from, K to) throws NoSuchNodeException, NoSuchRouteException;

    public Route shortestRouteBetweenNodes(K from, K to) throws NoSuchNodeException, NoSuchRouteException;

    public Route validateRoute(K ... nodes) throws NoSuchNodeException, NoSuchRouteException;
}
