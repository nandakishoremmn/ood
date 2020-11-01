package com.learn;

import com.learn.enums.Workout;
import com.learn.exceptions.BookingException;
import com.learn.exceptions.EntityNotFoundException;
import com.learn.factory.SlotFactory;
import com.learn.ifaces.Center;
import com.learn.ifaces.Slot;
import com.learn.ifaces.User;
import lombok.Builder;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
public class CenterImpl implements Center {
    String name;
    @Builder.Default
    @ToString.Exclude
    Map<String, List<Slot>> slots = new HashMap<>();

    @Override
    public void addSlot(String date, String startTime, String endTime, Workout workoutType, int capacity) {
        if (!isCenterFree(date, startTime)) {
            throw new RuntimeException("Slot not free for center");
        }
        if (!slots.containsKey(date)) {
            slots.put(date, new ArrayList<>());
        }

        Slot slot = SlotFactory.get(startTime, endTime, capacity, workoutType, this);
        slots.get(date).add(slot);
    }

    @Override
    public void addBooking(User user, String date, String startTime, Workout workoutType) {
        Slot slot = getSlot(date, startTime, workoutType);
        if (!slot.isAvailable()) {
            throw new BookingException("Slot " + slot + " is fully booked");
        }
        if (!user.isFree(date, startTime)) {
            throw new BookingException("User " + user + " not free at slot " + slot);
        }

        slot.addUser(user);
        user.addToSchedule(date, slot);

    }

    private boolean isCenterFree(String date, String startTime) {
        return !slots.containsKey(date) || slots.get(date).stream().noneMatch(s -> s.match(startTime));
    }

    @Override
    public void printWorkoutDetails(String date) {
        if (!slots.containsKey(date)) {
            System.out.println("No slots available for this date");
        }
        System.out.println("Workouts for center " + name + " at " + date);
        slots.get(date).forEach(Slot::printDetails);
    }

    private Slot getSlot(String date, String startTime, Workout workoutType) {
        if (!slots.containsKey(date)) {
            throw new EntityNotFoundException(String.format("Slot not found with given specs %s, %s:%s, %s", name, date, startTime, workoutType));
        }
        return slots.get(date).stream().filter(slot -> slot.match(startTime, workoutType))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Slot not found with given specs %s, %s:%s, %s", name, date, startTime, workoutType)));
    }

    @Override
    public String toString() {
        return name;
    }
}
