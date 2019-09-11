package ro.andreu.recipes.techs.railroad;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RouteParserTest {

    @Test
    public void parseTest() {
        RouteParser parser = new RouteParser();
        List<String> result = parser.parseRouteByDashSymbol("A-B-C-D-E");

        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
        assertEquals("D", result.get(3));
        assertEquals("E", result.get(4));
    }
}
