package data_sources;

import base_classes.Bus;
import base_classes.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Generates {@code Bus} data that uses existing {@code Stop} and {@code Route} data, but is entirely new with each
 * invocation. Buses will not be consistent across invocations of {@link #getNewData()}, making this a great model of
 * the bus system at any instant (ideal for testing), but not over time. For a "live" model, see {@link LiveBusModel}.
 */
public class InstantBusModel extends BusDataSource {
    public void getNewData() {
        Route[] routes = DatabaseService.getAllRoutes();
        HashMap<Long, HashMap<Long, Bus[]>> newOverview = new HashMap<>();

        for (Route route : routes) {
            newOverview.put(
                    route.getRouteId(),
                    generateBusData(
                            route.getRouteId(),
                            route.getStopIds(),
                            .25,
                            1,
                            3
                    )
            );
        }

        this.busDataByRouteId = newOverview;
    }

    /**
     * Generates a random mapping of stop IDs included in {@code stopIds} to an array of
     * randomly generated {@code Bus} instances in ascending order of seconds to arrival.
     *
     * @param routeId the routeId to associate new {@code Bus}es
     * @param percentWithApproachingBuses the percent (0-1.0) of Stop IDs which should have
     *       {@code Bus}es "actively" approaching them
     * @param minNumOfBusesPerStop the minimum number of {@code Bus}es to generate approaching
     *      {@code percentWithApproachingBuses} percent of Stop IDs
     * @param maxNumOfBusesPerStop the maximum number of {@code Bus}es to generate approaching
     *      {@code percentWithApproachingBuses} percent of Stop IDs
     *
     * @return a mapping of unique {@code Stop} instances (each with logical, valid data) mapped
     * to their stop IDs, with the number of entries between {@code minNum} and {@code maxNum}
     * (inclusive)
     */
    private HashMap<Long, Bus[]> generateBusData(
            long routeId,
            long[] stopIds,
            double percentWithApproachingBuses,
            int minNumOfBusesPerStop,
            int maxNumOfBusesPerStop
    ) {
        // The number of brand-new Buses to generate
        int numOfBusesToGenerate;
        double selection;

        // Values constant throughout iterations
        HashMap<Long, Bus[]> newOverview = new HashMap<>();
        int numOfStops = stopIds.length;

        // Holds the new fields generated by each iteration of below
        Bus[] newBusArray;
        Bus newBus;
        List<Double> newSTAs;
        double lowestSTA;

        // Add buses to each Stop along the Route
        for (long stopId : stopIds) {
            selection = Math.random();
            if (selection <= percentWithApproachingBuses) {
                numOfBusesToGenerate = minNumOfBusesPerStop + (int)(Math.random() * (maxNumOfBusesPerStop - minNumOfBusesPerStop));
                newBusArray = new Bus[numOfBusesToGenerate];
                lowestSTA = -10.0; // Buses currently at a stop go negative (-10 = 10 seconds at stop)

                for (int i = 0; i < numOfBusesToGenerate; i++) {
                    newSTAs = randomSTAs(numOfStops, lowestSTA);
                    lowestSTA = newSTAs.get(0);
                    newBus = new Bus(
                            randomBusId(),
                            routeId,
                            stopId,
                            newSTAs
                    );

                    newBusArray[i] = newBus;
                }

                newOverview.put(stopId, newBusArray);
            } else {
                newOverview.put(stopId, new Bus[]{});
            }
        }

        return newOverview;
    }

    // HELPERS
    /**
     * Generates a {@code double} array of length 0-{@code maxToGenerate} containing random
     * "seconds to arrival" values in ascending order.
     *
     * @param maxToGenerate the maximum number of random seconds to arrival values to include in the
     *      array
     *
     * @return a {@code List<Double>} containing random "seconds to arrival" values in ascending order, the size
     * of which anywhere from 0-{@code maxToGenerate}
     */
    private static List<Double> randomSTAs(int maxToGenerate, double minSTA) {
        int numOfSTAsToGenerate = 1 + (int)(Math.random() * (maxToGenerate - 1));
        ArrayList<Double> newSTAs = new ArrayList<Double>();

        double currentMin = minSTA;
        double newSTA, maxSTA = 1800; // Max STA is 30 minutes

        for (int i = 1; i <= numOfSTAsToGenerate; i++) {
            newSTA = currentMin + (Math.random() * (maxSTA - currentMin));
            newSTAs.add(newSTA);
        }

        return newSTAs;
    }

    /**
     * Generates a random 4-digit number between 1,000 and 9,999.
     *
     * @return a random 4-digit number between 1,000 and 9,999 not used by any other buses previously
     * generated by this class
     */
    private long randomBusId() {
        long newId;

        // While EXTREMELY unlikely, ensure no duplicate IDs are generated
        do {
            newId = 1000L + (long) (Math.random() * (9999L));
        } while (busIdAlreadyExists(newId));

        return newId;
    }

    /**
     * Determines if the provided {@code busId} already exists in the example batch
     * {@code busDataByRouteId}.
     *
     * @param busId the ID to check
     *
     * @return {@code true} if {@code busId} already exists in {@code busDataByRouteId},
     * else {@code false}
     */
    private boolean busIdAlreadyExists(long busId) {
        if (busDataByRouteId.isEmpty()) {
            return false;
        }

        Bus[] activeBuses;
        for (HashMap<Long, Bus[]> busOverview : busDataByRouteId.values()) {
            activeBuses = (Bus[])busOverview.values().toArray();
            for (Bus bus : activeBuses) {
                if (bus.getBusId() == busId) {
                    return true;
                }
            }
        }

        return false;
    }
}