package ro.andreu.recipes.techs.graph;

import java.util.List;

public interface Route {

    public List<Node> nodes();

    public List<Edge> edges();

    public void addNode(Node node);

    public int size();

    public Node actual();

    public boolean isNext();

    public Node next();

    public Node first();
}
