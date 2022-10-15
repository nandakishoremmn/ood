import java.util.HashMap;
import java.util.Map;

public class DriverServiceImpl implements DriverService {
    Map<String, Driver> drivers = new HashMap<>();

    @Override
    public void register(String driverName) {
        if(drivers.containsKey(driverName)) {
            throw new RuntimeException("duplicate driver");
        }
        drivers.put(driverName, Driver.builder()
                .name(driverName)
                .vehicles(new HashMap<>())
                .status(DriverStatus.IDLE)
                .build());
    }

    @Override
    public Driver get(String driverName) {
        if(!drivers.containsKey(driverName)) {
            throw new RuntimeException("driver not found");
        }
        return drivers.get(driverName);
    }

    @Override
    public void addVehicle(String driverName, String regNo, int seats) {
        Driver driver = get(driverName);
        driver.addVehicle(regNo, seats);
    }

    @Override
    public Vehicle getVehicle(String driverName, String regNo) {
        return get(driverName).getVehicle(regNo);
    }
}
