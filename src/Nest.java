import java.awt.*;

public class Nest extends Entity {
    public static final int MAX_HEALTH = 2500;
    public static final int ARMOR = 3;
    public static final int SIZE = 150;
    public Nest(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, SIZE);
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawImage(Util.NEST, (int)this.getLocation().getX(), (int)this.getLocation().getY(), null);
        g.setColor(Color.RED);
        g.fillRect((int)this.getLocation().x - 5, (int)this.getLocation().y + 15, getSize() * 2 + 10, 20);
        g.setColor(Color.GREEN);
        g.fillRect((int)this.getLocation().x - 5, (int)this.getLocation().y + 15, (int)((getSize() * 2 + 10) * ((1.0 * getHealth())/(2500.0))), 20);
        g.setColor(Color.BLACK);
        g.drawRect((int)getLocation().x - 5, (int)this.getLocation().y + 15, getSize() * 2 + 10, 20);
    }
}