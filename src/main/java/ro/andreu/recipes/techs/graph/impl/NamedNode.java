package ro.andreu.recipes.techs.graph.impl;

import org.springframework.util.StringUtils;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;

import java.util.HashSet;
import java.util.Set;

public class NamedNode implements Node {

    private String name;

    private Set<Edge> edges;

    private Set<Node> nodes;

    public NamedNode() {
        edges = new HashSet<>();
        nodes = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(NamedNodeEdge edge) {
        edge.setFrom(this);
        edges.add(edge);
        nodes.add(edge.to());
    }

    @Override
    public Set<Edge> edges() {
        return edges;
    }

    @Override
    public Set<Node> neighbours() {
        return nodes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedNode) {
            NamedNode node = (NamedNode) obj;
            String nodeName = node.getName();
            return !StringUtils.isEmpty(nodeName) && !StringUtils.isEmpty(name) && nodeName.equals(name);
        }
        return false;
    }
}
