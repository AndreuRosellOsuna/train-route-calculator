package ro.andreu.recipes.techs.graph.importer;

import ro.andreu.recipes.techs.graph.Graph;

import java.io.FileNotFoundException;

public interface GraphImporter<G extends Graph, R extends GraphImporterResource> {

    public Graph importGraph(R resource) throws FileNotFoundException;
}
