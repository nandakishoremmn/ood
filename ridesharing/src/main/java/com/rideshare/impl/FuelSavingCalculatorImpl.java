package com.rideshare.impl;

import com.rideshare.ifaces.FuelSavingCalculator;
import com.rideshare.ifaces.Ride;
import com.rideshare.ifaces.User;

public class FuelSavingCalculatorImpl implements FuelSavingCalculator {
    @Override
    public int getFuelSavedByUser(User user) {
        return user.getTakenRides().stream().map(Ride::getRideTime).reduce(0, Integer::sum);
    }
}
