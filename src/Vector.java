import java.awt.Point;
public class Vector {
    double x;
    double y;
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Vector(Point point){
        this.x = point.x;
        this.y = point.y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public static Vector randomVector()
    {
        double randX = ((Math.random()*600))+100.0;
        double randY = ((Math.random()*600))+100.0;
        return new Vector(randX,randY);
    }
    public double distanceFrom(Vector other){
        return Math.sqrt(  (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y) );
    }
}