package com.learn;

import com.learn.enums.Workout;
import com.learn.ifaces.Center;
import com.learn.ifaces.Slot;
import com.learn.ifaces.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class SlotImpl implements Slot {
    private String startTime;
    private String endTime;
    private int capacity;
    private Workout type;
    @ToString.Exclude
    private Center center;
    @Builder.Default
    @ToString.Exclude
    private List<User> users;

    public SlotImpl(String startTime, String endTime, int capacity, Workout type, Center center) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.type = type;
        this.center = center;
        this.users = new ArrayList<>();
    }

    @Override
    public boolean isAvailable() {
        return capacity > users.size();
    }

    @Override
    public void printDetails() {
        System.out.printf("Center: %s, Time: %s-%s, Workout: %s ,Remaining: %d/%d%n",
                center, startTime, endTime, type.toString(), capacity - users.size(), capacity);
    }

    @Override
    public boolean match(String startTime, Workout workoutType) {
        return this.type.equals(workoutType) &&
                this.match(startTime);
    }

    @Override
    public boolean match(String time) {
        return startTime.equals(time);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
