package delivery;
import java.util.*;
public final class FastestRouteStrategy extends ScoredRouteStrategy {
 protected double score(Route route, DeliveryRequest request, List<Route> eligible) { return route.finalMinutes(); }
 protected String formula() { return "base minutes + traffic penalty + weather penalty"; }
}
