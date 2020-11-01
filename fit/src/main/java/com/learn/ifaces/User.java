package com.learn.ifaces;

public interface User {
    boolean isFree(String date, String time);

    void addToSchedule(String date, Slot slot);

    void printSchedule(String date);
}
