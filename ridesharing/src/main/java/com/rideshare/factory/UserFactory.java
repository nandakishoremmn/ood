package com.rideshare.factory;

import com.rideshare.dto.UserDetail;
import com.rideshare.ifaces.User;
import com.rideshare.impl.UserImpl;

public class UserFactory {
    public static User get(UserDetail userDetail){
        UserImpl user = new UserImpl(userDetail.getUsername());
        userDetail.getVehicles().forEach(user::addVehicle);
        return user;
    }
}
