import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Enemy
{
    private boolean alive = true;
    private int health, armor, damage, vision, attackSpeed, size, money;
    private Map map;
    private Vector location;
    private Entity target;
    private Vector moveComponent;
    private int cooldown = 0;
    private double moveSpeed = 0;
    private ArrayList<Bird> birdsInVision = new ArrayList<>();
    private int range = 5;
    private int maxHealth;
    private double angle = 0;
    private BufferedImage image;
    private boolean angleChanged = false;
    private BufferedImage current = image;
    public Enemy(Map map, int health, int armor, Vector Location, int damage, int vision, int attackSpeed, double moveSpeed, int size, BufferedImage image, int money)
    {
        this.map = map;
        this.health = health;
        this.armor = armor;
        this.location = Location;
        this.damage = damage;
        this.vision = vision;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed / 10.0;
        this.size = size;
        this.maxHealth = health;
        this.target = map.getNest();
        this.image = image;
        this.money = money;
        this.angle = this.getCenter().getAngleTo(target.getCenter());
        current = Util.rotateDegrees(image, -1 * (int) angle + 90);
    }
    public Vector getCenter(){
        return new Vector(location.x + size, location.y + size);
    }
    public void draw(Graphics g){
        if(angleChanged) {
            current = Util.rotateDegrees(image, -1 * (int) angle + 90); //rotates image if angle has been changed
        }
        g.drawImage(current, (int) location.x, (int) location.y, null);
        //g.setColor(Color.BLACK);
        //g.drawOval((int)this.getCenter().x - vision, (int)this.getCenter().y - vision, vision * 2, vision * 2);

    }
    public double getMoveSpeed() { return moveSpeed; }
    public int getHealth()
    {
        return health;
    }
    public int getArmor()
    {
        return armor;
    }
    public Vector getLocation()
    {
        return location;
    }
    public int getDamage()
    {
        return damage;
    }
    public int getVision()
    {
        return vision;
    }
    public int getAttackSpeed()
    {
        return attackSpeed;
    }
    public int getSize() { return size; }
    public Map getMap()
    {
        return map;
    }
    public void exist() //called every frame
    {
        angleChanged = false; //assumes angle has not been changed
        cooldown--; //lowers countdowns for attacks
        updateTarget(); //updates the targets in vision in case player has placed a tower

        if(this.canAttack(target)) //if the target is in attack range
        {
            if(cooldown <= 0)
            {
                this.attack(target);
                cooldown = attackSpeed; //resets cooldown counter
            }
        }
        else { //if the target is not in range, moves
            this.location.x += moveComponent.x;
            this.location.y += moveComponent.y;

        }

    }
    public void setMoveComponent(Vector other){ //Calculates the x and y components of the steps taken each frame
        double distance = this.getCenter().distanceFrom(other);
        double time = distance / ((double)moveSpeed);
        double xDistance = other.x - location.x;
        double yDistance = other.y - location.y;
        moveComponent = new Vector(xDistance / ((double)time), yDistance / ((double)time));

    }
    public boolean inVision(Entity other){ return this.getCenter().distanceFrom(other.getCenter()) - (this.size + other.getSize()) <= vision; }
    public boolean canAttack(Entity other) { return this.getCenter().distanceFrom(other.getCenter()) - (this.size + other.getSize()) <= range; }
    public void attack(Entity en)
    {
        en.takeDamage(damage);
        if(en.getHealth()<=0)
            updateTarget();
    }
    public void updateBirdsInVision()
    {
        birdsInVision = new ArrayList<>();
        ArrayList<Bird> birds = map.getBirds();
        for(Bird bird : birds){
            if(this.inVision(bird))
                birdsInVision.add(bird);
        }
    }
    public void updateTarget()
    {
        Entity prev = target;
        updateBirdsInVision();
        if(birdsInVision.size() < 1) { //if no towers are in vision, heads towards nest
            target = this.getMap().getNest();
        }
        else { //moves towards closest tower
            double minDistance = Double.MAX_VALUE;
            for (Bird bird : birdsInVision) {
                double distance = bird.getLocation().distanceFrom(this.location) - (bird.getSize());
                if (distance < minDistance) {
                    target = bird;
                    minDistance = distance;
                }
            }
        }
        if(target != prev) { //updates angle if target has changed
            angle = this.getCenter().getAngleTo(target.getCenter());
            angleChanged = true;
        }
        setMoveComponent(target.getCenter()); //updates move component
    }
    public Entity getTarget() { return target; }
    public boolean isAlive() { return alive; }
    public void takeDamage(int damageGiven)
    {
        health -= Math.max((damageGiven-armor), 1);
        if(health <= 0) {
            alive = false;
            map.remove(this);
            map.player.addMoney(money);
        }
    }

}