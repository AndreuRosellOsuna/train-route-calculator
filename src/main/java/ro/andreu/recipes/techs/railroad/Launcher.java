package ro.andreu.recipes.techs.railroad;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

public class Launcher
{
    public static void main( String[] args )
    {
        SpringApplication app = new SpringApplication(CommandLineRouteCalculator.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }
}