package ro.andreu.recipes.techs.railroad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ro.andreu.recipes.techs.graph.importer.GraphImporter;

@SpringBootApplication
public class CommandLineRouteCalculator {

    @Autowired
    private GraphImporter importer;

}
