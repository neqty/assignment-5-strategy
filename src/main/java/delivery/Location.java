package delivery;
import java.util.*;
public record Location(String name, double latitude, double longitude) {
 public Location { Checks.text(name); if (!Double.isFinite(latitude) || !Double.isFinite(longitude) || Math.abs(latitude)>90 || Math.abs(longitude)>180) throw new IllegalArgumentException("Invalid coordinates"); }
}
