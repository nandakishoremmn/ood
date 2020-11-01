package com.rideshare.dto;

import com.rideshare.enums.Strategy;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RideSelectionOptions {
    String source;
    String destination;
    Strategy strategy;
}
