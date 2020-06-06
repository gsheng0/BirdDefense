import java.awt.Point;
public class Location{
    double x;
    double y;
    public Location(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Location (Point point){
        this.x = point.x;
        this.y = point.y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public double distanceFrom(Location other){
        return Math.sqrt(  (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y) );
    }
}