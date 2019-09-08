package ro.andreu.recipes.techs;

import ro.andreu.recipes.techs.graph.Graph;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.NoSuchNodeException;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;

public class FOO {

    Graph graph;

    void foo () throws NoSuchNodeException, NoSuchRouteException {
        Route route = graph.shortestRouteBetweenNodes("A", "B");
        int number = graph.differentRoutesBetweenNodes("C", "D");
        Number distance = graph.distanceAlongRoute(route);
    }
}
