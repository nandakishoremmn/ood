package com.learn.ifaces;

import com.learn.enums.Workout;

public interface Platform {
    void addUser(String username);

    void viewWorkouts(String centerName, String date);

    void bookWorkout(String username, String centerName, String date, String startTime, Workout workoutType);

    void showUserSchedule(String username, String date);
}
