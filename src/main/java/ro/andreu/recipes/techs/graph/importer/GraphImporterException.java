package ro.andreu.recipes.techs.graph.importer;

import ro.andreu.recipes.techs.graph.exception.AbstractGraphException;

public class GraphImporterException extends AbstractGraphException {
    public GraphImporterException(String description) {
        super(description);
    }
}
