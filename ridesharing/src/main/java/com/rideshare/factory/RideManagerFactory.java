package com.rideshare.factory;

import com.rideshare.ifaces.RideManager;
import com.rideshare.impl.RideManagerImpl;

public class RideManagerFactory {
    public static RideManager get(){
        return new RideManagerImpl();
    }
}
