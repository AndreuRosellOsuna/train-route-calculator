package ro.andreu.recipes.techs.graph.importer.csv;

import com.opencsv.bean.CsvBindAndSplitByName;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.impl.NamedNodeEdge;

import java.util.List;

public class SimpleCsvNamedNodeGraphImporterBean {

    public static final String REGEX_SPLITTER = "\\s*,\\s*|\\s+";

    @CsvBindAndSplitByName(elementType= Edge.class, splitOn = REGEX_SPLITTER, converter = SimpleCsvNamedNodeGraphImporterBeanConverter.class)
    private List<NamedNodeEdge> edges;

    public List<NamedNodeEdge> getEdges() {
        return edges;
    }
}
