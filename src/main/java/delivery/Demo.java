package delivery;
import java.util.*;
public class Demo {
 public static void main(String[] args) {
  var routes=DemoData.routes(); var request=new DeliveryRequest(DeliveryPriority.STANDARD,6000,60,false,20);
  var planner=new RoutePlanner(new FastestRouteStrategy());
  List<RouteStrategy> strategies=List.of(new FastestRouteStrategy(),new CheapestRouteStrategy(),new EcoFriendlyRouteStrategy(),new BalancedRouteStrategy(.4,.3,.1,.2));
  for(var strategy:strategies) { planner.setStrategy(strategy); System.out.println(planner.plan(routes,request)); }
  for(var priority:DeliveryPriority.values()) { var q=new DeliveryRequest(priority,6000,60,false,20); planner.setStrategy(StrategySelector.defaults().select(q)); System.out.println("AUTO "+priority+": "+planner.plan(routes,q)); }
  try { planner.plan(routes,new DeliveryRequest(DeliveryPriority.STANDARD,0,0,false,0)); } catch(NoSuitableRouteException e) { System.out.println("EXPECTED: "+e.getMessage()); }
 }
}
