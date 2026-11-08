package delivery;
import java.util.*;
public final class BalancedRouteStrategy extends ScoredRouteStrategy {
 private final double[] weights;
 public BalancedRouteStrategy(double time, double cost, double distance, double emission) {
  weights=new double[]{time,cost,distance,emission}; for(double w:weights) Checks.nonnegative(w);
  if(Math.abs(Arrays.stream(weights).sum()-1)>1e-9) throw new IllegalArgumentException("Weights must sum to 1");
 }
 private double metric(Route r, DeliveryRequest q, int index) { return switch(index) {case 0->r.finalMinutes(); case 1->r.totalCost(q); case 2->r.distanceKm(); default->r.carbonEmission();}; }
 protected double score(Route route, DeliveryRequest request, List<Route> eligible) {
  double result=0;
  for(int i=0;i<4;i++) { final int index=i;
   double min=eligible.stream().mapToDouble(r->metric(r,request,index)).min().orElseThrow();
   double max=eligible.stream().mapToDouble(r->metric(r,request,index)).max().orElseThrow();
   result+= weights[i]*(max==min?0:(metric(route,request,i)-min)/(max-min));
  } return result;
 }
 protected String formula() { return "Weighted min-max normalization over eligible routes, weights="+Arrays.toString(weights); }
}
