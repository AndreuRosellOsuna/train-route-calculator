package ro.andreu.recipes.techs.graph.importer.csv;

import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.springframework.util.StringUtils;
import ro.andreu.recipes.techs.graph.Edge;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.impl.NamedNode;
import ro.andreu.recipes.techs.graph.impl.NamedNodeEdge;

public class SimpleCsvNamedNodeGraphImporterBeanConverter extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        String from = value.substring(0,1);
        String to = value.substring(1,2);
        Integer distance = new Integer(value.substring(2, 3));

        NamedNodeEdge namedNodeEdge = new NamedNodeEdge();
        namedNodeEdge.setFrom(new NamedNode(from));
        namedNodeEdge.setTo(new NamedNode(to));
        namedNodeEdge.setDistance(distance);

        return namedNodeEdge;
    }
}
