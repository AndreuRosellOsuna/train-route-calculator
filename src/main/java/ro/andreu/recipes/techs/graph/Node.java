package ro.andreu.recipes.techs.graph;

import java.util.Set;

public interface Node {

    public Set<Edge> edges();

    public Set<Node> neighbours();
}
