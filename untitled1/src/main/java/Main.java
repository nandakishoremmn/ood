import java.util.List;

public class Main {
    public static void main(String[] args) {
        Application app = new Application();

        app.registerRider("rider1");
        app.registerRider("rider2");
        app.registerRider("rider3");

        app.registerDriver("driver1");
        app.registerDriver("driver2");
        app.registerDriver("driver3");

        app.addVehicle("driver1", "regno1", 4);

        app.offerRide("driver1", "regno1", "src1", "dest1");

        List<String> ridesFound = app.searchRide("src1", "dest1");

        app.selectRide("rider1", ridesFound.get(0));

//        app.riderCancel("rider1");
//
//        app.driverCancel("driver1");

        app.completeRide("driver1");

    }
}
