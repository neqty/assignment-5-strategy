package delivery;
import java.util.*;
public interface RouteStrategy {
 RouteDecision selectRoute(List<Route> routes, DeliveryRequest request);
 String getStrategyName();
}
