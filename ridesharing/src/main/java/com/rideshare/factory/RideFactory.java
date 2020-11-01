package com.rideshare.factory;

import com.rideshare.dto.RideDetail;
import com.rideshare.ifaces.Ride;
import com.rideshare.ifaces.User;
import com.rideshare.ifaces.Vehicle;
import com.rideshare.impl.RideImpl;

public class RideFactory {
    public static Ride get(Vehicle vehicle, RideDetail rideDetail) {
        return new RideImpl(vehicle, rideDetail);
    }
}
