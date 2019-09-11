package ro.andreu.recipes.techs.graph.importer.csv;

import ro.andreu.recipes.techs.graph.importer.GraphImporterResource;

public class SimpleCsvNamedNodeGraphImporterResource implements GraphImporterResource {

    String fileName;

    public SimpleCsvNamedNodeGraphImporterResource(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
