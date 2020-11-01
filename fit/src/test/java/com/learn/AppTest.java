package com.learn;

import com.learn.enums.Workout;
import com.learn.exceptions.BookingException;
import com.learn.exceptions.EntityNotFoundException;
import com.learn.exceptions.UserRegistrationException;
import com.learn.ifaces.Center;
import com.learn.ifaces.Platform;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.stream.IntStream;

public class AppTest {
    Platform platform;

    @Before
    public void setUp() {
        platform = createPlatform();
    }

    @Test
    public void addUsersToPlatform() {
        platform.addUser("1");
        platform.addUser("2");
    }

    @Test(expected = UserRegistrationException.class)
    public void addDuplicateUsersToPlatformFails() {
        platform.addUser("1");
        platform.addUser("1");
    }

    @Test
    public void viewSlots() {
        platform.viewWorkouts("koramangala", "20201207");
    }

    @Test
    public void bookSlotSuccessfullyAndShowSchedule() {
        addBulkUsers(3);
        platform.bookWorkout("0", "koramangala", "20201207", "0600", Workout.CARDIO);
        platform.bookWorkout("0", "bellandur", "20201207", "0700", Workout.WEIGHTS);
        platform.bookWorkout("0", "koramangala", "20201207", "0800", Workout.WEIGHTS);
        platform.bookWorkout("0", "koramangala", "20201207", "1800", Workout.CARDIO);
        platform.showUserSchedule("0", "20201207");
    }


    @Test
    public void bookInvalidSlot() {
        addBulkUsers(3);
        try {
            platform.bookWorkout("0", "koramangala", "20201207", "0600", Workout.WEIGHTS);
            Assert.fail();
        } catch (EntityNotFoundException e) {
            System.out.println(e);
        }
    }

    @Test
    public void bookConflictingSlotFails() {
        addBulkUsers(3);
        platform.bookWorkout("0", "koramangala", "20201207", "0600", Workout.CARDIO);
        platform.showUserSchedule("0", "20201207");
        try {
            platform.bookWorkout("0", "koramangala", "20201207", "0600", Workout.CARDIO);
            Assert.fail();
        } catch (BookingException e) {
            System.out.println(e);
        }
    }

    @Test
    public void bookFilledSlotFails() {
        addBulkUsers(4);
        platform.bookWorkout("0", "koramangala", "20201207", "0600", Workout.CARDIO);
        platform.bookWorkout("1", "koramangala", "20201207", "0600", Workout.CARDIO);
        platform.bookWorkout("1", "koramangala", "20201207", "0700", Workout.CARDIO);
        platform.bookWorkout("2", "koramangala", "20201207", "0600", Workout.CARDIO);
        platform.viewWorkouts("koramangala", "20201207");
        try {
            platform.bookWorkout("3", "koramangala", "20201207", "0600", Workout.CARDIO);
            Assert.fail();
        } catch (BookingException e) {
            System.out.println(e);
        }
    }

    private void addBulkUsers(int n) {
        IntStream.range(0, n).forEach(i -> platform.addUser("" + i));
    }

    private Platform createPlatform() {
        CenterImpl koramangala = CenterImpl.builder().name("koramangala").build();

        IntStream.range(1, 8).forEach(i -> {
            koramangala.addSlot(String.format("202012%02d", i), "0600", "0700", Workout.CARDIO, 3);
            koramangala.addSlot(String.format("202012%02d", i), "0700", "0800", Workout.CARDIO, 3);
            koramangala.addSlot(String.format("202012%02d", i), "0800", "0900", Workout.WEIGHTS, 3);
            koramangala.addSlot(String.format("202012%02d", i), "1800", "1900", Workout.CARDIO, 3);
            koramangala.addSlot(String.format("202012%02d", i), "1900", "2000", Workout.WEIGHTS, 3);
            koramangala.addSlot(String.format("202012%02d", i), "2000", "2100", Workout.CARDIO, 3);
        });

        CenterImpl bellandur = CenterImpl.builder().name("bellandur").build();

        IntStream.range(1, 8).forEach(i -> {
            bellandur.addSlot(String.format("202012%02d", i), "0600", "0700", Workout.CARDIO, 3);
            bellandur.addSlot(String.format("202012%02d", i), "0700", "0800", Workout.WEIGHTS, 3);
            bellandur.addSlot(String.format("202012%02d", i), "0800", "0900", Workout.WEIGHTS, 3);
            bellandur.addSlot(String.format("202012%02d", i), "1800", "1900", Workout.CARDIO, 3);
            bellandur.addSlot(String.format("202012%02d", i), "1900", "2000", Workout.WEIGHTS, 3);
            bellandur.addSlot(String.format("202012%02d", i), "2000", "2100", Workout.WEIGHTS, 3);
        });

        return App.builder()
                .users(new HashMap<>())
                .centers(new HashMap<String, Center>() {{
                    put("koramangala", koramangala);
                    put("bellandur", bellandur);
                }})
                .build();
    }

}
