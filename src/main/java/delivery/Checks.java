package delivery;
import java.util.*;
final class Checks {
 static double nonnegative(double value) { if (!Double.isFinite(value) || value < 0) throw new IllegalArgumentException("Expected finite nonnegative value"); return value; }
 static String text(String value) { if (value == null || value.isBlank()) throw new IllegalArgumentException("Text is required"); return value; }
}
