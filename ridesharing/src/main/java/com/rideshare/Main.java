package com.rideshare;

import com.rideshare.dto.RideDetail;
import com.rideshare.dto.RideSelectionOptions;
import com.rideshare.dto.UserDetail;
import com.rideshare.dto.VehicleDetails;
import com.rideshare.enums.Strategy;
import com.rideshare.factory.AppFactory;
import com.rideshare.ifaces.App;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        App app = AppFactory.get();

        IntStream.range(0, 10)
                .forEach(i -> app.addUser(UserDetail.builder()
                        .username("" + i)
                        .vehicles(Arrays.asList(
                                VehicleDetails.builder().name("1").build(),
                                VehicleDetails.builder().name("2").build()
                        ))
                        .build())
                );


        // 0 and 1 are offering rides
        app.offerRide("0", "1", RideDetail.builder()
                .source("A")
                .destination("B")
                .capacity(3)
                .startTime(5)
                .rideTime(5)
                .build());

        app.offerRide("1", "2", RideDetail.builder()
                .source("A")
                .destination("B")
                .capacity(3)
                .startTime(7)
                .rideTime(4)
                .build());

        // rest will take rides
        // 2 gets start 5 ride time 5
        app.selectRide("2", RideSelectionOptions.builder()
                .source("A")
                .destination("B")
                .strategy(Strategy.EARLIEST)
                .build());

        // 3 gets start 7 ride time 4
        app.selectRide("3", RideSelectionOptions.builder()
                .source("A")
                .destination("B")
                .strategy(Strategy.FASTEST)
                .build());

        // unavailable source
        app.selectRide("4", RideSelectionOptions.builder()
                .source("Z")
                .destination("B")
                .strategy(Strategy.FASTEST)
                .build());


        // fill up seats
        IntStream.range(5, 9).forEach(i -> app.selectRide("" + i, RideSelectionOptions.builder()
                .source("A")
                .destination("B")
                .strategy(Strategy.FASTEST)
                .build())
        );
        // fails
        app.selectRide("9", RideSelectionOptions.builder()
                .source("A")
                .destination("B")
                .strategy(Strategy.FASTEST)
                .build());

        IntStream.range(0, 10).forEach(i -> {
            app.getFuelSaved("" + i);
        });

        // user 2 is offering a ride but user 3 is unable to book as 3 is not free at the ride time
        // (still finishing the previous ride) but user 7 can take
        app.offerRide("2", "2", RideDetail.builder()
                .source("C")
                .destination("D")
                .capacity(3)
                .startTime(10)
                .rideTime(5)
                .build());
        app.selectRide("3", RideSelectionOptions.builder()
                .source("C")
                .destination("D")
                .strategy(Strategy.FASTEST)
                .build());
        app.selectRide("7", RideSelectionOptions.builder()
                .source("C")
                .destination("D")
                .strategy(Strategy.FASTEST)
                .build());

    }
}
