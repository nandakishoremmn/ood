import java.util.List;

public interface RideService {
    void offerRide(String driverName, String regNo, String src, String dest);

    List<String> searchRide(String src, String dest);


    void selectRide(String rider, String regNo);

    void completeRide(String driverName);
}
