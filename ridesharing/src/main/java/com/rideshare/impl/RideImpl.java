package com.rideshare.impl;

import com.rideshare.dto.RideDetail;
import com.rideshare.exceptions.RideSelectionException;
import com.rideshare.ifaces.Ride;
import com.rideshare.ifaces.User;
import com.rideshare.ifaces.Vehicle;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class RideImpl implements Ride {
    private final Vehicle vehicle;
    private final String source;
    private final String destination;
    private final int startTime;
    private final int rideTime;
    private final int capacity;
    private final List<User> users = new ArrayList<>();

    public RideImpl(Vehicle vehicle, RideDetail rideDetail) {
        this.vehicle = vehicle;
        this.source = rideDetail.getSource();
        this.destination = rideDetail.getDestination();
        this.startTime = rideDetail.getStartTime();
        this.rideTime = rideDetail.getRideTime();
        this.capacity = rideDetail.getCapacity();
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public int getStartTime() {
        return startTime;
    }

    @Override
    public int getRideTime() {
        return rideTime;
    }

    @Override
    public int getEndTime() {
        return startTime + rideTime;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public int getAvailableSeats() {
        return capacity - users.size();
    }

    @Override
    synchronized public void addUser(User user) {
        if(users.stream().anyMatch(u -> u.equals(user))){ // aleardy filtered but required for concurrency issues
            throw new RideSelectionException("User [" + user.getUsername() + "] has already taken this ride");
        }
        if(!(getAvailableSeats() > 0)){ // aleardy filtered but required for concurrency issues
            throw new RideSelectionException("This ride is full : " + this);
        }
        users.add(user);
        user.addTakenRide(this);
    }
}
