package com.rideshare.ifaces;

import java.util.List;

public interface Ride {

    Vehicle getVehicle();

    String getSource();

    String getDestination();

    int getStartTime();

    int getRideTime();

    int getEndTime();

    int getCapacity();

    List<User> getUsers();

    int getAvailableSeats();

    void addUser(User user);
}
