package com.rideshare.factory;

import com.rideshare.dto.VehicleDetails;
import com.rideshare.ifaces.User;
import com.rideshare.ifaces.Vehicle;
import com.rideshare.impl.VehicleImpl;

public class VehicleFactory {
    public static Vehicle get(User user, VehicleDetails vehicleDetails){
        return new VehicleImpl(vehicleDetails, user);
    }
}
