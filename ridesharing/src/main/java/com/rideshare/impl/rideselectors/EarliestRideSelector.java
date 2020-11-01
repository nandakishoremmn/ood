package com.rideshare.impl.rideselectors;

import com.rideshare.dto.RideSelectionOptions;
import com.rideshare.exceptions.RideSelectionException;
import com.rideshare.ifaces.Ride;
import com.rideshare.ifaces.RideSelector;

import java.util.Comparator;
import java.util.List;

public class EarliestRideSelector implements RideSelector {
    @Override
    public Ride selectRide(List<Ride> rides, RideSelectionOptions options) {
        return rides.stream()
                .min(Comparator.comparingInt(o -> o.getStartTime() + o.getRideTime()))
                .orElseThrow(() -> new RideSelectionException("No rides found"));
    }
}
