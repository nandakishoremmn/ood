package com.rideshare.factory;

import com.rideshare.ifaces.App;
import com.rideshare.impl.AppImpl;

public class AppFactory {
    static App app = new AppImpl();

    public static App get() {
        return app;
    }
}
