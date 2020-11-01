package com.rideshare.impl;

import com.rideshare.dto.RideDetail;
import com.rideshare.dto.RideSelectionOptions;
import com.rideshare.dto.UserDetail;
import com.rideshare.exceptions.EntityExistsException;
import com.rideshare.exceptions.EntityNotFoundException;
import com.rideshare.factory.FuelSavingCalculatorFactory;
import com.rideshare.factory.RideManagerFactory;
import com.rideshare.factory.UserFactory;
import com.rideshare.ifaces.RideManager;
import com.rideshare.ifaces.User;
import com.rideshare.ifaces.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class AppImpl implements com.rideshare.ifaces.App {
    Map<String, User> users = new HashMap<String, User>();
    RideManager rideManager = RideManagerFactory.get();

    public void addUser(UserDetail userdetail) {
        if(users.containsKey(userdetail.getUsername())){
            throw new EntityExistsException("Username already exists : " + userdetail.getUsername());
        }
        User user = UserFactory.get(userdetail);
        users.put(user.getUsername(), user);
    }

    public void offerRide(String username, String vehicleName, RideDetail rideDetail) {
        User user = getUser(username);
        Vehicle vehicle = user.getVehicle(vehicleName);
        rideManager.addRide(vehicle, rideDetail);
    }

    public void selectRide(String username, RideSelectionOptions options) {
        User user = getUser(username);
        rideManager.selectRide(user, options);
    }

    public void getFuelSaved(String username) {
        User user = getUser(username);
        int fuelSaved = FuelSavingCalculatorFactory.get().getFuelSavedByUser(user);
        System.out.printf("User %s saved %d units of fuel\n", user, fuelSaved);
    }

    private User getUser(String username) {
        if (!users.containsKey(username)) {
            throw new EntityNotFoundException("Username " + username + " not found");
        }
        return users.get(username);
    }


}
