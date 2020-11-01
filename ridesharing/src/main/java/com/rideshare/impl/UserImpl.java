package com.rideshare.impl;

import com.rideshare.dto.RideDetail;
import com.rideshare.dto.VehicleDetails;
import com.rideshare.exceptions.EntityExistsException;
import com.rideshare.factory.VehicleFactory;
import com.rideshare.ifaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class UserImpl implements User {
    private final String username;
    private final Map<String, Vehicle> vehicles = new HashMap<>();
    private final List<Ride> offeredRides = new ArrayList<>();
    private final List<Ride> takenRides = new ArrayList<>();

    public UserImpl(String username) {
        this.username = username;
    }

    public void addVehicle(VehicleDetails vehicleDetails) {
        if(vehicles.containsKey(vehicleDetails.getName())){
            throw new EntityExistsException("Vehicle [" + vehicleDetails.getName() + "] already present for user " + username);
        }
        Vehicle vehicle = VehicleFactory.get(this, vehicleDetails);
        vehicles.put(vehicle.getName(), vehicle);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Vehicle getVehicle(String vehicleName) {
        if(!vehicles.containsKey(vehicleName)){
            throw new EntityExistsException("Vehicle [" + vehicleName + "] not found for user " + username);
        }
        return vehicles.get(vehicleName);
    }

    @Override
    public void addTakenRide(Ride ride) {
        takenRides.add(ride);
    }

    @Override
    public void addOfferedRide(Ride ride) {
        offeredRides.add(ride);
    }

    @Override
    public List<Ride> getTakenRides() {
        return takenRides;
    }

    @Override
    public List<Ride> getOfferedRides() {
        return offeredRides;
    }

    @Override
    public boolean isFreeBetween(int startTime, int endTime) {
        return Stream.concat(offeredRides.stream(), takenRides.stream())
                .noneMatch(r -> {
                    return (r.getStartTime() >= startTime && r.getStartTime() < endTime ) ||
                            (r.getEndTime() > startTime && r.getEndTime() <= endTime);
                });
    }

    @Override
    public String toString() {
        return username;
    }
}
