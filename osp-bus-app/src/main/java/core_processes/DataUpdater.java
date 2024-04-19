package core_processes;

import data_sources.BusData;

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
        /* System.out.println("ROUTES:---------------------");
        Route[] allRoutes = DatabaseService.getAllRoutes();
        for (Route route : allRoutes) {
            System.out.println(route);
        }
        Stop[] allStops = DatabaseService.getAllStops();
        for (Stop stop : allStops) {
            System.out.println(stop);
        } */
    }
}