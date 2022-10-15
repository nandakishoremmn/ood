public interface DriverService {
    void register(String driverName);

    Driver get(String driverName);

    void addVehicle(String driverName, String regNo, int seats);

    Vehicle getVehicle(String driverName, String regNo);
}
