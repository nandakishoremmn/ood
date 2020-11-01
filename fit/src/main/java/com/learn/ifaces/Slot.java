package com.learn.ifaces;

import com.learn.enums.Workout;

public interface Slot {
    boolean isAvailable();

    void printDetails();

    boolean match(String startTime, Workout workoutType);

    boolean match(String time);

    void addUser(User user);
}
