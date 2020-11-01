package com.learn;

import java.util.HashMap;
import java.util.Map;

import com.learn.enums.Workout;
import com.learn.exceptions.EntityNotFoundException;
import com.learn.exceptions.UserRegistrationException;

import com.learn.factory.UserFactory;
import com.learn.ifaces.Center;
import com.learn.ifaces.Platform;
import com.learn.ifaces.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App implements Platform {
    private Map<String, Center> centers = new HashMap<>();
    private Map<String, User> users = new HashMap<>();

    @Override
    public void addUser(String username) {
        if (!isRegisteredUser(username)) {
            users.put(username, UserFactory.get(username));
        } else {
            throw new UserRegistrationException("Username " + username + " exists");
        }
    }

    @Override
    public void viewWorkouts(String centerName, String date) {
        Center center = getCenter(centerName);
        center.printWorkoutDetails(date);
    }

    @Override
    public void bookWorkout(String username, String centerName, String date, String startTime, Workout workoutType){
        User user = getUser(username);
        Center center = getCenter(centerName);

        center.addBooking(user, date, startTime, workoutType);
    }

    @Override
    public void showUserSchedule(String username, String date){
        User user = getUser(username);
        user.printSchedule(date);
    }

    private boolean isRegisteredUser(String username) {
        return users.containsKey(username);
    }

    private User getUser(String username) {
        if(!users.containsKey(username)){
            throw new EntityNotFoundException("User " + username + " not found");
        }
        return users.get(username);
    }

    private Center getCenter(String name){
        if(!centers.containsKey(name)){
            throw new EntityNotFoundException("Center not found : " + name);
        }
        return centers.get(name);
    }
}
