package org.smart.home;

import org.smart.home.factory.ApplianceFactory;
import org.smart.home.interfaces.Appliance;
import org.smart.home.interfaces.Device;
import org.smart.home.interfaces.Home;

import java.util.HashMap;
import java.util.Map;

public class HomeImpl implements Home {
    private final Map<String, Device> devices = new HashMap<String, Device>();
    private final Map<String, Appliance> appliances = new HashMap<>();

    public Device addDevice(String name, String activator) {
        System.out.println("Adding device " + name + " " + activator);
        if (devices.containsKey(name)) {
            System.out.println("Device already exists");
        } else {
            devices.put(name, new DeviceImpl(name, activator, this));
            System.out.println("Device added");
        }
        return devices.get(name);
    }

    public void sendCommand(String command) {
        System.out.println(command);
        devices.forEach((key, value) -> value.sendCommand(command));
    }

    public void printDevice(String deviceName) {
        if (!devices.containsKey(deviceName)) {
            System.out.println("Device not found");
        } else {
            devices.get(deviceName).print();
        }
    }

    @Override
    public void addAppliance(String command) {
        System.out.println(command);
        boolean res = devices.values().stream().anyMatch(device -> device.addAppliance(command));
        if(!res) {
            System.out.println("Could not add appliance");
        }
    }

    @Override
    public Appliance getAppliance(String name) {
        if(appliances.containsKey(name)) {
            return appliances.get(name);
        } else {
            throw new RuntimeException("Appliance not found");
        }
    }

    @Override
    public void createAppliance(String name) {
        System.out.println("Creating appliance " + name);
        try {
            Appliance appliance = ApplianceFactory.create(name);
            appliances.put(appliance.getName(), appliance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
