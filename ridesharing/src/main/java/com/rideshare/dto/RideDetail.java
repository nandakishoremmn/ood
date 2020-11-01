package com.rideshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideDetail {
    String source;
    String destination;
    int startTime;
    int rideTime;
    int capacity;
}
