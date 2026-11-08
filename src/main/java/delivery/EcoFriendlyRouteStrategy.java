package delivery;
import java.util.*;
public final class EcoFriendlyRouteStrategy extends ScoredRouteStrategy {
 protected double score(Route route, DeliveryRequest request, List<Route> eligible) { return route.carbonEmission()*0.8 + route.distanceKm()*0.2; }
 protected String formula() { return "CO2 * 0.8 + distance * 0.2"; }
}
