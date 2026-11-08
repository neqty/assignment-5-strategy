package delivery;
import java.util.*;
public abstract class ScoredRouteStrategy implements RouteStrategy {
 protected abstract double score(Route route, DeliveryRequest request, List<Route> eligible);
 protected abstract String formula();
 public String getStrategyName() { return getClass().getSimpleName(); }
 public final RouteDecision selectRoute(List<Route> routes, DeliveryRequest request) {
  Objects.requireNonNull(routes); Objects.requireNonNull(request);
  Map<String,String> rejected = new LinkedHashMap<>(); List<Route> eligible = new ArrayList<>(); Set<String> ids = new HashSet<>();
  for(Route route: routes) {
   Objects.requireNonNull(route); if(!ids.add(route.id())) throw new IllegalArgumentException("Duplicate route id");
   List<String> reasons = new ArrayList<>();
   if(!route.available()) reasons.add("Unavailable");
   if(route.totalCost(request)>request.maxBudget()) reasons.add("Budget exceeded");
   if(route.finalMinutes()>request.maxDeliveryMinutes()) reasons.add("Deadline exceeded");
   if(route.carbonEmission()>request.maxEmission()) reasons.add("Emission limit exceeded");
   if(reasons.isEmpty()) eligible.add(route); else rejected.put(route.id(),String.join("; ",reasons));
  }
  if(eligible.isEmpty()) throw new NoSuitableRouteException("No eligible routes: " + rejected);
  Route best = eligible.stream().min(Comparator.comparingDouble((Route r)->score(r,request,eligible)).thenComparing(Route::id)).orElseThrow();
  double value=score(best,request,eligible);
  return new RouteDecision(getStrategyName(),best,value,formula()+"; lowest score="+value+"; base time="+best.estimatedMinutes()+", traffic="+best.trafficPenalty()+", weather="+best.weatherPenalty()+", final time="+best.finalMinutes()+", total cost="+best.totalCost(request)+", CO2="+best.carbonEmission()+"; ties resolved by route id",rejected);
 }
}
