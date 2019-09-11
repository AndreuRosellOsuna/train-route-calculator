package ro.andreu.recipes.techs.graph.importer.csv;

import org.springframework.stereotype.Component;
import ro.andreu.recipes.techs.graph.importer.GraphImporterResource;

@Component
public class SimpleCsvNamedNodeGraphImporterResource implements GraphImporterResource {

    private String fileName;

    public SimpleCsvNamedNodeGraphImporterResource() {
    }

    public SimpleCsvNamedNodeGraphImporterResource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getResource() {
        return this.fileName;
    }

    @Override
    public void setResource(String resource) {
        this.fileName = resource;;
    }
}
