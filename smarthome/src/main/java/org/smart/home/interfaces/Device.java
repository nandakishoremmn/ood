package org.smart.home.interfaces;

public interface Device {
    boolean sendCommand(String command);
    boolean addAppliance(String command);
    void print();
}
