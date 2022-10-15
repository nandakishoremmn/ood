import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Ride {
    String src;
    String dest;
    Driver driver;
    Vehicle vehicle;
    Set<String> riders;
}
