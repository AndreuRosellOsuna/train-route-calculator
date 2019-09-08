package ro.andreu.recipes.techs.graph.impl;

import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NamedNodeRoute implements Route {

    private List<Node> nodes;

    public NamedNodeRoute(List<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public NamedNodeRoute() {
        this.nodes = new ArrayList<>();
    }

    @Override
    public List<Node> nodes() {
        return nodes;
    }

    @Override
    public void addNode(Node node){
        nodes.add(node);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Node actual() {
        return null;
    }

    @Override
    public boolean isNext() {
        return false;
    }

    @Override
    public Node next() {
        return null;
    }

    @Override
    public Node first() {
        return null;
    }
}
