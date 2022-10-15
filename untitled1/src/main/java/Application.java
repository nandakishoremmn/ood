import java.util.List;

public class Application {
    private final RiderService riderService;
    private final DriverService driverService;
    private final RideService rideService;

    public Application() {
        riderService = new RiderServiceImpl();
        driverService = new DriverServiceImpl();
        rideService = new RideServiceImpl(driverService, riderService);
    }

    public void registerRider(String riderName) {
        riderService.register(riderName);
    }

    public void registerDriver(String driverName) {
        driverService.register(driverName);
    }

    public void addVehicle(String driverName, String regNo, int seats) {
        driverService.addVehicle(driverName, regNo, seats);
    }

    public void offerRide(String driverName, String regNo, String src, String dest) {
        rideService.offerRide(driverName, regNo, src, dest);
    }

    public List<String> searchRide(String src, String dest) {
        return rideService.searchRide(src, dest);
    }

    public void selectRide(String rider, String regNo) {
        rideService.selectRide(rider, regNo);
    }

    public void completeRide(String driverName) {
        rideService.completeRide(driverName);
    }
}
