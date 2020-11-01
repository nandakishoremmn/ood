package com.rideshare.ifaces;

import com.rideshare.dto.VehicleDetails;

import java.util.List;

public interface User {
    void addVehicle(VehicleDetails vehicleDetails);
    boolean isFreeBetween(int startTime, int endTime);
    Vehicle getVehicle(String vehicleName);

    String getUsername();
    void addTakenRide(Ride ride);
    void addOfferedRide(Ride ride);
    List<Ride> getTakenRides();
    List<Ride> getOfferedRides();
}
