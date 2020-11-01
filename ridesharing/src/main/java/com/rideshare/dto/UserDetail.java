package com.rideshare.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDetail {
    String username;
    @Builder.Default
    List<VehicleDetails> vehicles = new ArrayList<>();
}
