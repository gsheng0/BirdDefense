import java.util.*;

public class Enemy
{
    boolean alive = true;
    int health, armor, damage, range, attackSpeed;
    Map map;
    Vector location;
    Bird target;
    private Vector moveComponent;
    private int cooldown = 0;
    private int moveSpeed = 0;
    private ArrayList<Bird> birdsInRange = new ArrayList<Bird>();
    public Enemy(int health, int armor, Vector Location, int damage, int range, int attackSpeed, int moveSpeed)
    {
        this.health = health;
        this.armor = armor;
        this.location = Location;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed;
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
    public int getRange()
    {
        return range;
    }
    public int getAttackSpeed()
    {
        return attackSpeed;
    }
    public Map getMap()
    {
        return map;
    }
    public void exist() //called every frame
    {
        cooldown--;
        updateTarget();
        if(target != null){
            if(birdsInRange.contains(target)) //in range
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
        else {
            setMoveComponent(new Vector(500, 400));
            this.location.x += moveComponent.x;
            this.location.y += moveComponent.y;

        }
    }
    public void setMoveComponent(Vector other){
        double distance = this.location.distanceFrom(other);
        double time = distance / ((double)moveSpeed);
        double xDistance = other.x - location.x;
        double yDistance = other.y - location.y;
        moveComponent = new Vector(xDistance / ((double)time), yDistance / ((double)time));

    }
    public void attack(Bird bird)
    {
        target.takeDamage(damage);
        if(target.getHealth()<=0)
            updateTarget();
    }
    public void updateBirdsInRange()
    {
        birdsInRange = new ArrayList<>();
        ArrayList<Bird> birds = map.getBirds();
        for(Bird bird : birds){
            if(bird.getLocation().distanceFrom(this.location) <= range)
                birdsInRange.add(bird);
        }
    }
    public void updateTarget()
    {
        if(birdsInRange.size() < 1) {
            target = null;
            return;
        }
        double minDistance=Double.MAX_VALUE;
        for(Bird bird: birdsInRange)
        {
            double distance = bird.getLocation().distanceFrom(this.location);
            if(distance<minDistance)
            {
                target = bird;
                minDistance = distance;
            }
        }

        setMoveComponent(target.getLocation());
    }
    public Bird getTarget()
    {
        return target;
    }
    public boolean isAlive()
    {
        return alive;
    }
    public void takeDamage(int damageGiven)
    {
        health -= (damageGiven-armor);
        if(health <= 0)
            alive = false;
    }

}