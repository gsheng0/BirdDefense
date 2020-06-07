import java.awt.*;

public class Egg extends Projectile{
    private int counter = 0;
    public Egg(Map map, Vector location, Vector stepVector){
        super(map, location, stepVector, 33, 18, Util.EGG);
    }

    @Override
    public void draw(Graphics g) {
        counter += (int)(Math.random() * 2) + 1;
        g.drawImage(Util.rotateDegrees(Util.EGG, counter), (int)getLocation().x, (int)getLocation().y, null);
    }
}
