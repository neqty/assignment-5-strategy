package delivery;
import java.util.*;
public final class StrategySelector {
 private final Map<DeliveryPriority,RouteStrategy> strategies;
 public StrategySelector(Map<DeliveryPriority,RouteStrategy> strategies) { this.strategies=Map.copyOf(strategies); }
 public RouteStrategy select(DeliveryRequest request) {
  Objects.requireNonNull(request);
  DeliveryPriority key=request.priority()==DeliveryPriority.STANDARD && request.ecoPreferred()?DeliveryPriority.ECO:request.priority();
  return Objects.requireNonNull(strategies.get(key),"No strategy registered for "+key);
 }
 public static StrategySelector defaults() { return new StrategySelector(Map.of(DeliveryPriority.URGENT,new FastestRouteStrategy(),DeliveryPriority.ECONOMY,new CheapestRouteStrategy(),DeliveryPriority.ECO,new EcoFriendlyRouteStrategy(),DeliveryPriority.STANDARD,new BalancedRouteStrategy(.4,.3,.1,.2))); }
}
