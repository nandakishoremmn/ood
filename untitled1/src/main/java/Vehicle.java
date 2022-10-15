import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {
    String regNo;
    Integer seats;
    VehicleStatus status;
}
