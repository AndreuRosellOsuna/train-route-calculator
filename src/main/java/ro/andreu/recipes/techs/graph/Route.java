package ro.andreu.recipes.techs.graph;

import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import java.util.List;

public interface Route {

    public List<Node> nodes();

    public List<Edge> edges();

    public void addNode(Node node) throws NoSuchRouteException;
}
