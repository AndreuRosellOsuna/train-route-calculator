package ro.andreu.recipes.techs.graph.impl;

import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;

public class NamedNodeEdge implements Edge {

    private NamedNode from;

    private NamedNode to;

    private Number distance;

    public void setFrom(NamedNode from) {
        this.from = from;
    }

    public void setTo(NamedNode to) {
        this.to = to;
    }

    public void setDistance(Number distance) {
        this.distance = distance;
    }

    @Override
    public NamedNode from() {
        return from;
    }

    @Override
    public NamedNode to() {
        return to;
    }

    @Override
    public Number distance() {
        return distance;
    }
}
