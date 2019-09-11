package ro.andreu.recipes.techs.graph.importer;

import ro.andreu.recipes.techs.graph.Graph;

public interface GraphImporter<G extends Graph, R extends GraphImporterResource> {

    public Graph importGraph(R resource) throws GraphImporterException;
}
