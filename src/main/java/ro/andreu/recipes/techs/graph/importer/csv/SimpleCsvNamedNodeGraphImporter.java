package ro.andreu.recipes.techs.graph.importer.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.andreu.recipes.techs.graph.exception.MalformedException;
import ro.andreu.recipes.techs.graph.impl.NamedNodeEdge;
import ro.andreu.recipes.techs.graph.impl.NamedNodeGraph;
import ro.andreu.recipes.techs.graph.importer.GraphImporter;
import ro.andreu.recipes.techs.graph.importer.GraphImporterException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class providing the service to collect data from a csv file
 */
@Component
public class SimpleCsvNamedNodeGraphImporter implements GraphImporter<NamedNodeGraph, SimpleCsvNamedNodeGraphImporterResource> {

    private Logger logger = LoggerFactory.getLogger(SimpleCsvNamedNodeGraphImporter.class);

    @Override
    public NamedNodeGraph importGraph(SimpleCsvNamedNodeGraphImporterResource railroadFile) throws GraphImporterException {
        List<SimpleCsvNamedNodeGraphImporterBean> beansList = null;
        try {
            beansList = new CsvToBeanBuilder(new FileReader(railroadFile.getResource()))
                    .withType(SimpleCsvNamedNodeGraphImporterBean.class).withSeparator('|').build().parse();
        } catch (FileNotFoundException e) {
            throw new GraphImporterException("Railroad file configuration not found - file " + railroadFile.getResource());
        }

        List<NamedNodeEdge> allEdges = beansList.stream().flatMap(bean -> bean.getEdges().stream()).collect(Collectors.toList());

        NamedNodeGraph graph = new NamedNodeGraph();
        allEdges.stream().forEach(edge -> {
            try {
                graph.addEdge(edge);
            } catch (MalformedException e) {
                logger.error("Error adding edge with from : {}, to : {}, distance : {} properties to new graph", edge.from(), edge.to(), edge.distance());
            }
        });

        return graph;
    }
}
