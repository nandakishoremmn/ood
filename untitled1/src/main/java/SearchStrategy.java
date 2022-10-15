import java.util.List;
import java.util.Map;

public interface SearchStrategy {
    List<Ride> search(SearchRequest request, Map<String, Ride> rides);
}
