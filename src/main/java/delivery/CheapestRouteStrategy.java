package delivery;
import java.util.*;
public final class CheapestRouteStrategy extends ScoredRouteStrategy {
 protected double score(Route route, DeliveryRequest request, List<Route> eligible) { return route.totalCost(request); }
 protected String formula() { return "base cost + distance * 50 + fees + urgent surcharge 500"; }
}
