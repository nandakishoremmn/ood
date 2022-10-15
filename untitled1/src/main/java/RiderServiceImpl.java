import java.util.HashMap;
import java.util.Map;

public class RiderServiceImpl implements RiderService {
    Map<String, Rider> riders = new HashMap<>();

    public void register(String riderName) {
        if(riders.containsKey(riderName)) {
            throw new RuntimeException("Duplicate rider");
        }
        riders.put(riderName, new Rider(riderName));
    }

    @Override
    public Rider get(String riderName) {
        if(!riders.containsKey(riderName)){
            throw new RuntimeException("rider not found");
        }
        return riders.get(riderName);
    }
}
