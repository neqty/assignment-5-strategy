package delivery;
import java.util.*;
public record DeliveryRequest(DeliveryPriority priority, double maxBudget, int maxDeliveryMinutes, boolean ecoPreferred, double maxEmission) {
 public DeliveryRequest { Objects.requireNonNull(priority); Checks.nonnegative(maxBudget); Checks.nonnegative(maxEmission); if(maxDeliveryMinutes<0) throw new IllegalArgumentException("Negative deadline"); }
}
