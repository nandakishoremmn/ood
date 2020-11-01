package com.rideshare.factory;

import com.rideshare.enums.Strategy;
import com.rideshare.ifaces.RideSelector;
import com.rideshare.impl.rideselectors.EarliestRideSelector;
import com.rideshare.impl.rideselectors.FastestRideSelector;

public class RideSelectorFactory {
    public static final FastestRideSelector fastestRideSelector = new FastestRideSelector();
    public static final EarliestRideSelector earliestRideSelector = new EarliestRideSelector();
    public static RideSelector get(Strategy strategy) {
        switch (strategy){
            case FASTEST:
                return fastestRideSelector;
            case EARLIEST:
                return earliestRideSelector;
            default:
                throw new UnsupportedOperationException("This strategy is not implemented : " + strategy);
        }
    }
}
