package delivery;
import java.util.*;
public final class DemoData {
 public static List<Route> routes() {
  Location a=new Location("Warehouse",51.16,71.43), b=new Location("Hospital",51.12,71.45);
  return List.of(new Route("A",a,b,10,20,3000,6,15,0,100,true),new Route("B",a,b,14,22,4000,8,2,0,200,true),new Route("C",a,b,8,40,2000,1,0,0,0,true),new Route("D",a,b,16,35,700,5,5,0,0,true),new Route("E",a,b,6,45,1600,.4,0,0,0,true),new Route("F",a,b,5,10,100,.1,0,0,0,false),new Route("G",a,b,20,18,7000,10,0,4,300,true),new Route("H",a,b,12,30,2200,3,5,2,50,true));
 }
}
