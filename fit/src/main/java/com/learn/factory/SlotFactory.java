package com.learn.factory;

import com.learn.SlotImpl;
import com.learn.enums.Workout;
import com.learn.ifaces.Center;
import com.learn.ifaces.Slot;

public class SlotFactory {

    public static Slot get(String startTime, String endTime, int capacity, Workout type, Center center) {
        return new SlotImpl(startTime, endTime, capacity, type, center);
    }
}
