package com.learn;

import com.learn.ifaces.Slot;
import com.learn.ifaces.User;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserImpl implements User {
    private String username;
    @ToString.Exclude
    private Map<String, List<Slot>> bookings = new HashMap<>();
    
    public UserImpl(String username){
        this.username = username;
    }

    @Override
    public boolean isFree(String date, String time) {
        return !bookings.containsKey(date) || bookings.get(date).stream().noneMatch(slot -> {
            return slot.match(time);
        });
    }

    @Override
    public void addToSchedule(String date, Slot slot) {
        if(!bookings.containsKey(date)){
            bookings.put(date, new ArrayList<>());
        }
        bookings.get(date).add(slot);
    }

    @Override
    public void printSchedule(String date) {
        if(!bookings.containsKey(date)){
            System.out.println("Schedule clear");
        }
        System.out.printf("Schedule for %s at %s%n", username, date);
        bookings.get(date).forEach(Slot::printDetails);
    }
}
