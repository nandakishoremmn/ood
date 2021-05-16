package org.smart.home.interfaces;

public interface Home {
    Device addDevice(String name, String activator);
    void sendCommand(String command);
    void printDevice(String deviceName);
    void addAppliance(String name);
    Appliance getAppliance(String name);
    void createAppliance(String name);
}
