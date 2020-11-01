package com.rideshare.ifaces;

import com.rideshare.dto.RideSelectionOptions;

import java.util.List;

public interface RideSelector {
    Ride selectRide(List<Ride> rides, RideSelectionOptions options);
}
