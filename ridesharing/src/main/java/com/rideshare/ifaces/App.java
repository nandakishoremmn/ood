package com.rideshare.ifaces;

import com.rideshare.dto.RideDetail;
import com.rideshare.dto.RideSelectionOptions;
import com.rideshare.dto.UserDetail;

public interface App {
    void addUser(UserDetail userdetail);
    void offerRide(String username, String vehicleName, RideDetail rideDetail);
    void selectRide(String username, RideSelectionOptions options);
    void getFuelSaved(String user);
}
