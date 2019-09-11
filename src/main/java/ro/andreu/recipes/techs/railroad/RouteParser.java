package ro.andreu.recipes.techs.railroad;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RouteParser {

    public List<String> parseRouteByDashSymbol(String route) {
        return Arrays.asList(route.split("-"));
    }
}
