package core_processes;

import base_classes.Route;
import base_classes.Stop;
import data_sources.BusData;
import data_sources.DatabaseService;

import java.util.TimerTask;

/**
 * Specifies what happens during each data update.
 */
public class DataUpdater extends TimerTask {

    /**
     * Updates all {@link base_classes.Bus} data.
     */
    @Override
    public void run() {
        BusData.updateBusData();

        // Validate updates are occurring
        System.out.println("LATEST SNAPSHOT OF DATA BELOW:");
        System.out.println("ROUTES:-----------------------");
        Route[] allRoutes = DatabaseService.getAllRoutes();
        for (Route route : allRoutes) {
            System.out.println(route.toString());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        System.out.println("STOPS:------------------------");
        Stop[] allStops = DatabaseService.getAllStops();
        for (Stop stop : allStops) {
            System.out.println(stop.toString());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}