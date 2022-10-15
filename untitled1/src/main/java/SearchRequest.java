import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchRequest {
    String src;
    String dest;
}
