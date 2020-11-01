package com.rideshare.ifaces;

import com.rideshare.dto.RideDetail;
import com.rideshare.dto.RideSelectionOptions;

public interface RideManager {
    void addRide(Vehicle vehicle, RideDetail rideDetail);
    void selectRide(User user, RideSelectionOptions options);
}
