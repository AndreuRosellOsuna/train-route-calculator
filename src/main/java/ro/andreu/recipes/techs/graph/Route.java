package ro.andreu.recipes.techs.graph;

import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

import java.util.List;

public interface Route {

    public List<Node> nodes();

    public List<Edge> edges();

    public void addNode(Node node) throws NoSuchRouteException;

//    public void addEdge(Edge edge);

    public int size();

    public Node actual();

    public boolean isNext();

    public Node next();

    public Node first();
}
