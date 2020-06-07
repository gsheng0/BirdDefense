import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;
public class Bird extends Entity
{
    private int damage, range, attackSpeed, size;
    private Enemy target;
    private ArrayList<Enemy> enemiesInRange = new ArrayList<>();
    private int cooldown;
    private double angle = 0.0;
    private BufferedImage image;
    public Bird(Map map, int health, int armor, Vector location, int size, int damage, int range, int attackSpeed, BufferedImage image)
    {
        super(map, health, armor, location, size);
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.image = image;
    }

    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackSpeed() { return attackSpeed; }
    public void draw(Graphics g)
    {
        BufferedImage rotated = Util.rotateDegrees(image, -1 * (int)angle + 90);
        g.drawImage(rotated, (int)this.getLocation().x, (int)this.getLocation().y, null);
        //g.setColor(Color.BLACK);
        //g.drawOval((int)this.getCenter().x - range, (int)(this.getCenter().y - range), range * 2, range * 2);
    }
    public void attack(Enemy en){
        en.takeDamage(this.damage);
    }
    public void updateEnemiesInRange(){
        enemiesInRange = new ArrayList<Enemy>();
        ArrayList<Enemy> enemies = this.getMap().getEnemies();
        for(Enemy enemy : enemies){
            if(this.inRange(enemy))
                enemiesInRange.add(enemy);
        }
    }
    public boolean inRange(Enemy en){ return this.getCenter().distanceFrom(en.getCenter()) - (this.size + en.getSize()) <= range; }
    public void updateTarget(){
        updateEnemiesInRange();
        if(enemiesInRange.size() < 1) {
            target = null;
            return;
        }
        double closest = 10000000000.0;
        for(Enemy enemy : enemiesInRange){
            double distance = enemy.getLocation().distanceFrom(this.getLocation());
            if(distance < closest)
            {
                target = enemy;
                closest = distance;
            }
        }
    }
    public void exist(){
        updateTarget();
        if(cooldown <= 0){
            if(target != null){
                this.attack(target);
                cooldown = attackSpeed;
            }
        }
        else cooldown--;

        if(target != null)
            angle = this.getCenter().getAngleTo(target.getCenter());

    }

}