package org.smart.home.interfaces;

public interface Device {
    void sendCommand(String command);
    boolean addAppliance(String command);
    void print();
}
