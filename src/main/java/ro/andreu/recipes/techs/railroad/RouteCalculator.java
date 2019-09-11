package ro.andreu.recipes.techs.railroad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ro.andreu.recipes.techs.graph.Graph;
import ro.andreu.recipes.techs.graph.Node;
import ro.andreu.recipes.techs.graph.Route;
import ro.andreu.recipes.techs.graph.exception.NoSuchNodeException;
import ro.andreu.recipes.techs.graph.exception.NoSuchRouteException;
import ro.andreu.recipes.techs.graph.importer.GraphImporter;
import ro.andreu.recipes.techs.graph.importer.GraphImporterException;
import ro.andreu.recipes.techs.graph.importer.GraphImporterResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@ComponentScan("ro.andreu.recipes.techs")
public class RouteCalculator implements ApplicationRunner{

    Logger logger = LoggerFactory.getLogger("RC_CONSOLE");

    private static final String HELP_PARAMETER = "help";
    private static final String RAILROADFILE_PARAMETER = "railroadFile";
    private static final String COMPUTE_PARAMETER = "compute";
    private static final String ROUTE_PARAMETER = "route";

    @Autowired
    private GraphImporter importer;

    @Autowired
    private GraphImporterResource resource;

    @Autowired
    private RouteParser parser;

    @Override
    public void run(ApplicationArguments args) {

        if(args.containsOption(HELP_PARAMETER)) {
            showUsage();
            System.exit(0);
        }

        String railroadFile = validateAndReturnParameter(args, RAILROADFILE_PARAMETER);
        if(railroadFile == null) {
            parameterRequired(RAILROADFILE_PARAMETER);
        }

        String compute = validateAndReturnParameter(args, COMPUTE_PARAMETER);
        if(compute == null) {
            parameterRequired(COMPUTE_PARAMETER);
        }

        Actions action = null;

        try {
            action = Actions.valueOf(compute);
        } catch (RuntimeException e) {
            logger.error("Parameter {} in {} option is invalid", compute, COMPUTE_PARAMETER);
            System.exit(1);
        }

        String routeArg = validateAndReturnParameter(args, ROUTE_PARAMETER);
        if(routeArg == null) {
            parameterRequired(ROUTE_PARAMETER);
        }

        List<String> townsRoute = parser.parseRouteByDashSymbol(routeArg);
        if(townsRoute == null) {
            logger.error("Malformed route");
            System.exit(1);
        } else if(townsRoute.size() == 0) {
            logger.error("No route declared");
            System.exit(1);
        } else if(townsRoute.size() > 2 && !Actions.distance.equals(action)) {
            logger.error("Two many towns to compute " + action.name());
            System.exit(1);
        }

        resource.setResource(railroadFile);

        Graph graph = null;
        try {
            graph = importer.importGraph(resource);
        } catch (GraphImporterException e) {
            catchError(e.description());
        }

        switch (action) {
            case distance:
                try {
                    Route newGraphRoute = graph.validateRoute(townsRoute.toArray());
                    Number distance = graph.distanceAlongRoute(newGraphRoute);
                    logger.info("Distance : {}", distance.intValue());
                } catch (NoSuchNodeException | NoSuchRouteException e) {
                    catchError(e.description());
                }
                break;
            case number:
                try {
                    int numberOfRoutes = graph.differentRoutesBetweenNodes(townsRoute.get(0), townsRoute.get(1));
                    logger.info("Number of different routes : {}", numberOfRoutes);
                } catch (NoSuchNodeException | NoSuchRouteException e) {
                    catchError(e.description());
                }
                break;
            case shortest:
                try {
                    Route shortestRoute = graph.shortestRouteBetweenNodes(townsRoute.get(0), townsRoute.get(1));
                    List<Node> nodes = shortestRoute.nodes();

                    StringBuilder builder = new StringBuilder();
                    builder.append("The shortest route is ");
                    nodes.stream().forEach(node -> builder.append(node.toString()).append("-"));
                    builder.deleteCharAt(builder.lastIndexOf("-"));
                    logger.info(builder.toString());
                } catch (NoSuchNodeException | NoSuchRouteException e) {
                    catchError(e.description());
                }

                break;
            default:
                break;
        }

        System.exit(0);
    }

    private void catchError(String description) {
        logger.error(description.replaceAll("node", "town").replaceAll("graph", "railroad"));
        System.exit(1);
    }

    private void showUsage() {
        logger.info("Usage");
        logger.info("--{} : Show this information", HELP_PARAMETER);
        logger.info("");

        logger.info("--{} : Path to file containing information about rail road", RAILROADFILE_PARAMETER);
        logger.info("   Example : --{}={}", RAILROADFILE_PARAMETER, "railroad.csv");
        logger.info("");

        logger.info("--{} : Action to be performed", COMPUTE_PARAMETER);
        logger.info("   Options : ");
        Arrays.stream(Actions.values()).forEach(action -> logger.info("         * {} : {}", action.name(), action.description));
        logger.info("   Example : --{}={}", COMPUTE_PARAMETER, Actions.shortest.name());
        logger.info("");

        logger.info("--{} : Name of towns separated by dash", ROUTE_PARAMETER);
        logger.info("   Example : --{}=A-B-C", ROUTE_PARAMETER);
    }


    private void parameterRequired(String parameter) {
        logger.error("{} parameter is required", parameter);
        System.exit(1);
    }

    private String validateAndReturnParameter(ApplicationArguments args, String parameter) {
        if(args.containsOption(parameter) && args.getOptionValues(parameter).size() == 1) {
            return args.getOptionValues(parameter).get(0);
        } else {
            return null;
        }
    }

    private enum Actions {

        distance("Compute the distance along a certain route"),
        number("Compute the number of different routes between two towns"),
        shortest("Compute the shortest route between two towns");

        String description;

        Actions(String description) {
            this.description = description;
        }
    }
}
