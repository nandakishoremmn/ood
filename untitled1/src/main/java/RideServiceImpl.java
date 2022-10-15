import java.util.*;
import java.util.stream.Collectors;

public class RideServiceImpl implements RideService {
    Map<String, Ride> rides;
    Set<String> busyDrivers;

    SearchStrategy searchStrategy = new SearchStrategyImpl();

    private final DriverService driverService;
    private final RiderService riderService;
    public RideServiceImpl(DriverService driverService, RiderService riderService) {
        this.driverService = driverService;
        this.riderService = riderService;
        this.rides = new HashMap<>();
        busyDrivers = new HashSet<>();
    }

    @Override
    public void offerRide(String driverName, String regNo, String src, String dest) {
        Driver driver = driverService.get(driverName);
        Vehicle vehicle = driverService.getVehicle(driverName, regNo);
        if(rides.containsKey(vehicle.regNo)) {
            throw new RuntimeException("vehicle busy");
        }
        if(busyDrivers.contains(driverName)){
            throw new RuntimeException("driver busy");
        }
        busyDrivers.add(driverName);
        rides.put(vehicle.getRegNo(), Ride.builder()
                .src(src)
                .dest(dest)
                .driver(driver)
                .riders(new HashSet<>())
                .vehicle(vehicle)
                .build());
    }

    @Override
    public List<String> searchRide(String src, String dest) {
        List<Ride> ridesFound = searchStrategy.search(SearchRequest.builder().src(src).dest(dest).build(), rides);
        return ridesFound.stream().map(ride -> ride.getVehicle().getRegNo()).collect(Collectors.toList());
    }

    @Override
    public void selectRide(String riderName, String regNo) {
        Rider rider = riderService.get(riderName);
        if(!rides.containsKey(regNo)) {
            throw new RuntimeException("vehicle is not available");
        }
        Ride ride = rides.get(regNo);
        if(ride.getVehicle().getStatus().equals(VehicleStatus.BOOKED)) {
            throw new RuntimeException("ride not avaialble");
        }
        if(ride.getDriver().getStatus().equals(DriverStatus.BOOKED)) {
            throw new RuntimeException("ride not avaialble");
        }
        if(ride.getRiders().size() >= ride.getVehicle().seats) {
            throw new RuntimeException("ride full");
        }
        if(ride.getRiders().contains(rider.getName())) {
            throw new RuntimeException("ride already taken by user");
        }
        ride.getDriver().setStatus(DriverStatus.BOOKED);
        ride.getVehicle().setStatus(VehicleStatus.BOOKED);
        ride.riders.add(riderName);
        busyDrivers.add(ride.getDriver().getName());
    }

    @Override
    public void completeRide(String driverName) {
        Driver driver = driverService.get(driverName);
        Vehicle vehicle = driver.getBusyVehicle();
        Ride ride = rides.get(vehicle.getRegNo());
        ride.driver.setStatus(DriverStatus.IDLE);
        ride.vehicle.setStatus(VehicleStatus.IDLE);
        busyDrivers.remove(driverName);
        rides.remove(vehicle.getRegNo());
    }
}
