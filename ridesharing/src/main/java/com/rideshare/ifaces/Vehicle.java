package com.rideshare.ifaces;

public interface Vehicle {
    String getName();
    User getOwner();
    boolean isFreeBetween(int startTime, int endTime);
}
