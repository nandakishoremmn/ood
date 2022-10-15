import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@ToString
@Builder
public class Driver {
    @Getter
    String name;
    Map<String, Vehicle> vehicles;
    @Getter
    @Setter
    DriverStatus status;

    public Vehicle getVehicle(String regNo) {
        if (!vehicles.containsKey(regNo)) {
            throw new RuntimeException("no vehicle found : " + regNo);
        }
        return vehicles.get(regNo);
    }

    public void addVehicle(String regNo, int seats) {
        if (vehicles.containsKey(regNo)) {
            throw new RuntimeException("duplicate vehicle");
        }
        if (seats <= 0) {
            throw new RuntimeException("invalid seat size");
        }
        vehicles.put(regNo, Vehicle.builder()
                .regNo(regNo)
                .seats(seats)
                .status(VehicleStatus.IDLE).build());
    }

    public Vehicle getBusyVehicle() {
        return vehicles.values().stream()
                .filter(vehicle -> vehicle.status.equals(VehicleStatus.BOOKED))
                .findFirst().orElseThrow(() -> new RuntimeException("driver has no vehicle to complete"));
    }
}
