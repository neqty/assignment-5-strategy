package delivery;
import java.util.*;
public record RouteDecision(String strategy, Route route, double score, String explanation, Map<String,String> rejected) {
 public RouteDecision { rejected = Map.copyOf(rejected); }
}
