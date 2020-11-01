package com.rideshare.impl;

import com.rideshare.dto.VehicleDetails;
import com.rideshare.ifaces.User;
import com.rideshare.ifaces.Vehicle;
import lombok.ToString;

@ToString
public class VehicleImpl implements Vehicle {
    private final String name;
    private final User owner;

    public VehicleImpl(VehicleDetails vehicleDetails, User owner) {
        this.name = vehicleDetails.getName();
        this.owner = owner;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public boolean isFreeBetween(int startTime, int endTime) {
        return owner.getOfferedRides().stream().filter(r -> r.getVehicle().equals(this))
                .noneMatch(r -> {
                    return (r.getStartTime() >= startTime && r.getStartTime() < endTime ) ||
                            (r.getEndTime() > startTime && r.getEndTime() <= endTime);
                });
    }
}
