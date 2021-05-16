package org.smart.home.factory;

import org.smart.home.appliances.Fan;
import org.smart.home.appliances.Light;
import org.smart.home.interfaces.Appliance;

public class ApplianceFactory {
    public static Appliance create(String name) {
        if(name.toLowerCase().endsWith("fan")){
            return new Fan(name);
        } else if(name.toLowerCase().endsWith("light")) {
            return new Light(name);
        } else {
            throw new RuntimeException("Unsupported appliance");
        }
    }
}
