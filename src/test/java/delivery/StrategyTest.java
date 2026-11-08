package delivery;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
class StrategyTest {
 final List<Route> routes=DemoData.routes();
 final DeliveryRequest q=new DeliveryRequest(DeliveryPriority.STANDARD,6000,60,false,20);
 RouteDecision choose(RouteStrategy s) { return s.selectRoute(routes,q); }
 @Test void fastestIncludesTraffic() { assertEquals("B",choose(new FastestRouteStrategy()).route().id()); }
 @Test void cheapestIncludesDistanceFees() { var d=choose(new CheapestRouteStrategy()); assertEquals("D",d.route().id()); assertEquals(1500,d.score()); }
 @Test void ecoUsesBothMetrics() { assertEquals("E",choose(new EcoFriendlyRouteStrategy()).route().id()); }
 @Test void balancedTimeOnlyMatchesFastest() { assertEquals("B",choose(new BalancedRouteStrategy(1,0,0,0)).route().id()); }
 @Test void balancedCostOnlyMatchesCheapest() { assertEquals("D",choose(new BalancedRouteStrategy(0,1,0,0)).route().id()); }
 @Test void switchRuntime() { var p=new RoutePlanner(new FastestRouteStrategy()); assertEquals("B",p.plan(routes,q).route().id()); p.setStrategy(new EcoFriendlyRouteStrategy()); assertEquals("E",p.plan(routes,q).route().id()); }
 @Test void autoSelection() { var s=StrategySelector.defaults(); assertInstanceOf(FastestRouteStrategy.class,s.select(new DeliveryRequest(DeliveryPriority.URGENT,6000,60,false,20))); assertInstanceOf(CheapestRouteStrategy.class,s.select(new DeliveryRequest(DeliveryPriority.ECONOMY,6000,60,false,20))); assertInstanceOf(EcoFriendlyRouteStrategy.class,s.select(new DeliveryRequest(DeliveryPriority.ECO,6000,60,false,20))); assertInstanceOf(BalancedRouteStrategy.class,s.select(q)); }
 @Test void ecoPreferenceForStandard() { assertInstanceOf(EcoFriendlyRouteStrategy.class,StrategySelector.defaults().select(new DeliveryRequest(DeliveryPriority.STANDARD,6000,60,true,20))); }
 @Test void unavailableExcluded() { assertEquals("Unavailable",choose(new FastestRouteStrategy()).rejected().get("F")); }
 @Test void budgetChangesWinner() { var d=new FastestRouteStrategy().selectRoute(routes,new DeliveryRequest(DeliveryPriority.STANDARD,2000,60,false,20)); assertEquals("D",d.route().id()); }
 @Test void deadlineRejectsEcoRoute() { var d=new EcoFriendlyRouteStrategy().selectRoute(routes,new DeliveryRequest(DeliveryPriority.STANDARD,6000,30,false,20)); assertEquals("B",d.route().id()); assertTrue(d.rejected().get("E").contains("Deadline")); }
 @Test void emissionLimitIsHard() { var d=new FastestRouteStrategy().selectRoute(routes,new DeliveryRequest(DeliveryPriority.STANDARD,6000,60,false,1)); assertEquals("C",d.route().id()); }
 @Test void impossibleRequestThrows() { assertThrows(NoSuitableRouteException.class,()->new FastestRouteStrategy().selectRoute(routes,new DeliveryRequest(DeliveryPriority.STANDARD,0,0,false,0))); }
 @Test void emptyRoutesThrows() { assertThrows(NoSuitableRouteException.class,()->new FastestRouteStrategy().selectRoute(List.of(),q)); }
 @Test void invalidWeightsRejected() { assertThrows(IllegalArgumentException.class,()->new BalancedRouteStrategy(.4,.4,.4,.4)); assertThrows(IllegalArgumentException.class,()->new BalancedRouteStrategy(Double.NaN,0,0,1)); assertThrows(IllegalArgumentException.class,()->new BalancedRouteStrategy(-1,1,1,0)); }
 @Test void fifthStrategy() { assertEquals("E",choose(new DistanceFirstRouteStrategy()).route().id()); }
 @Test void comparisonShowsDifferentResults() { assertNotEquals(choose(new FastestRouteStrategy()).route(),choose(new CheapestRouteStrategy()).route()); }
 @Test void exactBudgetDeadlineAndEmissionAccepted() { var d=new FastestRouteStrategy().selectRoute(List.of(routes.get(3)),new DeliveryRequest(DeliveryPriority.STANDARD,1500,40,false,5)); assertEquals("D",d.route().id()); }
 @Test void singleCandidateNormalizesToZero() { assertEquals(0,new BalancedRouteStrategy(.4,.3,.1,.2).selectRoute(List.of(routes.get(0)),q).score()); }
 @Test void negativeDomainRejected() { assertThrows(IllegalArgumentException.class,()->new DeliveryRequest(DeliveryPriority.STANDARD,-1,60,false,20)); }
 @Test void missingDataRejected() { assertThrows(NullPointerException.class,()->new FastestRouteStrategy().selectRoute(routes,null)); }
 @Test void urgentSurchargeApplied() { assertEquals(2000,routes.get(3).totalCost(new DeliveryRequest(DeliveryPriority.URGENT,6000,60,false,20))); }
 @Test void explanationIncludesReasonsAndFormula() { var d=choose(new FastestRouteStrategy()); assertTrue(d.explanation().contains("traffic")); assertTrue(d.rejected().get("G").contains("Budget")); }
 @Test void tieBreakIsDeterministic() { Route a=routes.get(0); Route z=new Route("Z",a.origin(),a.destination(),a.distanceKm(),a.estimatedMinutes(),a.cost(),a.carbonEmission(),a.trafficPenalty(),a.weatherPenalty(),a.additionalFees(),true); assertEquals("A",new FastestRouteStrategy().selectRoute(List.of(z,a),q).route().id()); }
}
