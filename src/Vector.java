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
    public Point getPoint()
    {
        return new Point((int)x, (int)y);
    }
    public double getAngleTo(Vector vector2)
    {
        Point p1 = getPoint();
        Point p2 = vector2.getPoint();
        double angle = Math.toDegrees(Math.atan2(p2.getY()-p1.getY(), p2.getX()-p1.getX()));
        angle = 360-angle;
        if(angle>=360)
            angle -= 360;
        return angle;
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
    public String toString(){
        return "[" + x + ", " + y + "]";
    }
}