package ro.andreu.recipes.techs.graph;

public interface Edge {

    public Node from();

    public Node to();

    public Number distance();
}
