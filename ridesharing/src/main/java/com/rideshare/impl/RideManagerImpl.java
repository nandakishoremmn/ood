package com.rideshare.impl;

import com.rideshare.dto.RideDetail;
import com.rideshare.dto.RideSelectionOptions;
import com.rideshare.exceptions.RideOfferException;
import com.rideshare.exceptions.RideSelectionException;
import com.rideshare.factory.RideFactory;
import com.rideshare.factory.RideSelectorFactory;
import com.rideshare.ifaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RideManagerImpl implements RideManager {
    List<Ride> rides = new ArrayList<>();

    @Override
    public void addRide(Vehicle vehicle, RideDetail rideDetail) {
        if(!vehicle.isFreeBetween(rideDetail.getStartTime(), rideDetail.getStartTime() + rideDetail.getRideTime())){
            throw new RideOfferException("Vehicle not free at this time");
        }
        Ride ride = RideFactory.get(vehicle, rideDetail);
        rides.add(ride);
        vehicle.getOwner().addOfferedRide(ride);
        System.out.println("Ride offered " + ride);
    }

    @Override
    public void selectRide(User user, RideSelectionOptions options) {
        try {
            RideSelector rideSelector = RideSelectorFactory.get(options.getStrategy());
            Ride ride = rideSelector.selectRide(rides
                    .stream()
                    .filter(r -> r.getAvailableSeats() > 0) // ride should have free seats
                    .filter(r -> r.getSource().equals(options.getSource())) // match source
                    .filter(r -> r.getDestination().equals(options.getDestination())) // match destination
                    .filter(r -> user.isFreeBetween(r.getStartTime(), r.getStartTime() + r.getRideTime())) // user should not have any ride at the time
                    .filter(r -> !r.getVehicle().getOwner().equals(user)) // user does not book his own ride
                    .collect(Collectors.toList()),
                    options);
            ride.addUser(user);
            System.out.printf("User [%s] added to ride [%s]\n", user.getUsername(), ride);
        } catch (RideSelectionException e){
            System.out.printf("Unable to select ride for %s with options %s due to : %s \n", user, options, e);
        }
    }
}
