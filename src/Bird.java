import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;
public class Bird extends Entity
{
    private int damage, range, attackSpeed, maxHealth;
    private Enemy target;
    private ArrayList<Enemy> enemiesInRange = new ArrayList<>();
    private int cooldown;
    private double angle = 0.0;
    private BufferedImage image;
    private boolean angleChanged = false;
    private BufferedImage current;
    public Bird(Map map, int health, int armor, Vector location, int size, int damage, int range, int attackSpeed, BufferedImage image)
    {
        super(map, health, armor, location, size);
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed * 10;
        this.image = image;
        this.maxHealth = health;
        current = image;

    }
    public Enemy getTarget() { return target; }
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackSpeed() { return attackSpeed; }
    public void draw(Graphics g)
    {
        if(angleChanged) { //updates image to be draw if angle has changed
            current = Util.rotateDegrees(image, -1 * (int) angle + 90);
        }
        g.drawImage(current, (int) this.getLocation().x, (int) this.getLocation().y, null);

        //drawing health bar
        g.setColor(Color.RED);
        g.fillRect((int)getLocation().x + 5, (int)getLocation().y - 10, getSize() * 2, 10);
        g.setColor(Color.GREEN);
        g.fillRect((int)getLocation().x + 5, (int)getLocation().y - 10, (int)(getSize() * 2 * ((1.0 * getHealth())/(1.0 * maxHealth))), 10);
        g.setColor(Color.BLACK);
        g.drawRect((int)getLocation().x + 5, (int)getLocation().y - 10, getSize() * 2, 10);
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
    public boolean inRange(Enemy en){ return this.getCenter().distanceFrom(en.getCenter()) - (this.getSize() + en.getSize()) <= range; }
    public void updateTarget(){
        Enemy prev = target;
        updateEnemiesInRange();
        if(enemiesInRange.size() < 1) {
            target = null;
            return;
        }
        double closest = 10000000000.0;
        for(Enemy enemy : enemiesInRange){ //chooses closest enemy to attack
            double distance = enemy.getLocation().distanceFrom(this.getLocation());
            if(distance < closest)
            {
                target = enemy;
                closest = distance;
            }
        }
        if(target != null && prev != target){ //changes angle only if there is a new target to attack
            angleChanged = true;
            angle = this.getCenter().getAngleTo(target.getCenter());
        }
    }
    public void exist(){ //called every frame
        angleChanged = false;
        updateTarget();

        if(cooldown <= 0){
            if(target != null){
                this.attack(target);
                cooldown = attackSpeed;
            }
        }
        else cooldown--;


    }

}