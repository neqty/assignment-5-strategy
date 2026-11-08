package delivery;
import java.util.*;
public record Route(String id, Location origin, Location destination, double distanceKm, int estimatedMinutes, double cost, double carbonEmission, int trafficPenalty, int weatherPenalty, double additionalFees, boolean available) {
 public Route { Checks.text(id); Objects.requireNonNull(origin); Objects.requireNonNull(destination); Checks.nonnegative(distanceKm); Checks.nonnegative(cost); Checks.nonnegative(carbonEmission); Checks.nonnegative(additionalFees); if(estimatedMinutes<0 || trafficPenalty<0 || weatherPenalty<0) throw new IllegalArgumentException("Negative time"); }
 public double finalMinutes() { return (double) estimatedMinutes + trafficPenalty + weatherPenalty; }
 public double totalCost(DeliveryRequest request) { return cost + distanceKm * 50 + additionalFees + (request.priority()==DeliveryPriority.URGENT ? 500 : 0); }
}
