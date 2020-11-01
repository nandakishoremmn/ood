package com.learn.ifaces;

import com.learn.enums.Workout;

public interface Center {
    void printWorkoutDetails(String date);

    void addSlot(String date, String startTime, String endTime, Workout workoutType, int capacity);

    void addBooking(User user, String date, String startTime, Workout workoutType);
}
