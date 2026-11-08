package delivery;
import java.util.*;
public final class DistanceFirstRouteStrategy extends ScoredRouteStrategy {
 protected double score(Route route, DeliveryRequest request, List<Route> eligible) { return route.distanceKm(); }
 protected String formula() { return "Minimum distance in km"; }
}
