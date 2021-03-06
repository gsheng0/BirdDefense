import java.awt.*;

public class MassiveBat extends Enemy{
    public static final int MAX_HEALTH = 2500;
    public static final int ARMOR = 20;
    public static final int DAMAGE = 99;
    public static final int VISION = 250;
    public static final int ATTACK_SPEED = 250;
    public static final double MOVE_SPEED = 0.12;
    public static final int SIZE = 40;
    public static final int MONEY = 10;
    public MassiveBat(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, DAMAGE, VISION, ATTACK_SPEED, MOVE_SPEED, SIZE, Util.MASSIVE_BAT, MONEY);
    }

    @Override
    public void takeDamage(int damageGiven) {
        super.takeDamage(damageGiven);
        if(this.getHealth() <= 0)
        {
            for(int i = 0; i < 15; i++)
                Bat.Factory.build(new Vector(this.getCenter().x + (int)(Math.random() * 41) - 20, this.getCenter().x + (int)(Math.random() * 41) - 20));
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.RED);
        g.fillRect((int)this.getLocation().x - 5, (int)this.getLocation().y , 210, 20);
        g.setColor(Color.GREEN);
        g.fillRect((int)this.getLocation().x - 5, (int)this.getLocation().y, (int)(210 * ((1.0 * getHealth())/(2500.0))), 20);
        g.setColor(Color.BLACK);
        g.drawRect((int)getLocation().x - 5, (int)this.getLocation().y, 210, 20);
    }

    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map; }
        public static MassiveBat build(Vector location){
            MassiveBat newest = new MassiveBat(map, location);
            map.add(newest);
            return newest;
        }
    }
}
