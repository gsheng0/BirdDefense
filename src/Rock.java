import java.awt.*;

public class Rock extends Projectile{
    private int counter = 0;
    public Rock(Map map, Vector location, Vector stepVector){ super(map, location, stepVector, 66, 20, Util.ROCK);}
    @Override
    public void draw(Graphics g) {
        counter += (int)(Math.random() * 2) + 1;
        g.drawImage(Util.rotateDegrees(Util.ROCK, (int)(counter/10.0)), (int)getLocation().x, (int)getLocation().y, null);
    }
}
