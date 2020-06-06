import java.awt.*;
import java.util.*;

public class Enemy
{
    private boolean alive = true;
    private int health, armor, damage, vision, attackSpeed, size;
    private Map map;
    private Vector location;
    private Entity target;
    private Vector moveComponent;
    private int cooldown = 0;
    private int moveSpeed = 0;
    private ArrayList<Bird> birdsInRange = new ArrayList<>();
    private int range = 5;
    public Enemy(Map map, int health, int armor, Vector Location, int damage, int vision, int attackSpeed, int moveSpeed, int size)
    {
        this.map = map;
        this.health = health;
        this.armor = armor;
        this.location = Location;
        this.damage = damage;
        this.vision = vision;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed;
        this.size = size;
    }
    public Vector getCenter(){
        return new Vector(location.x + size, location.y + size);
    }
    public void draw(Graphics g){
        g.setColor(Color.CYAN);
        g.fillOval((int)location.x, (int)location.y, size * 2, size * 2);
    }
    public int getMoveSpeed() { return moveSpeed; }
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
        cooldown--;
        updateTarget();

        if(this.canAttack(target))
        {
            if(cooldown <= 0)
            {
                this.attack(target);
                cooldown = attackSpeed;
            }
        }
        else {
            this.location.x += moveComponent.x;
            this.location.y += moveComponent.y;

        }

    }
    public void setMoveComponent(Vector other){
        double distance = this.getCenter().distanceFrom(other);
        double time = distance / ((double)moveSpeed);
        double xDistance = other.x - location.x;
        double yDistance = other.y - location.y;
        moveComponent = new Vector(xDistance / ((double)time), yDistance / ((double)time));

    }
    public boolean inRange(Entity other){ return this.getCenter().distanceFrom(other.getCenter()) - (this.size + other.getSize()) <= vision; }
    public boolean canAttack(Entity other) { return this.getCenter().distanceFrom(other.getCenter()) - (this.size + other.getSize()) <= range; }
    public void attack(Entity en)
    {
        en.takeDamage(damage);
        if(en.getHealth()<=0)
            updateTarget();
    }
    public void updateBirdsInRange()
    {
        birdsInRange = new ArrayList<>();
        ArrayList<Bird> birds = map.getBirds();
        for(Bird bird : birds){
            if(this.inRange(bird))
                birdsInRange.add(bird);
        }
        System.out.println("Birds in range: " + birdsInRange.size());
    }
    public void updateTarget()
    {
        updateBirdsInRange();
        if(birdsInRange.size() < 1) {
            target = this.getMap().getNest();
        }
        else {
            double minDistance = Double.MAX_VALUE;
            for (Bird bird : birdsInRange) {
                double distance = bird.getLocation().distanceFrom(this.location);
                if (distance < minDistance) {
                    target = bird;
                    minDistance = distance;
                }
            }
        }
        System.out.println("Target: " + target);
        System.out.println("\tTarget location: " + target.getLocation().x);
        setMoveComponent(target.getCenter());
    }
    public Entity getTarget() { return target; }
    public boolean isAlive() { return alive; }
    public void takeDamage(int damageGiven)
    {
        health -= (damageGiven-armor);
        if(health <= 0)
            alive = false;
    }

}