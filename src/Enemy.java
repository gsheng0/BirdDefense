import java.util.*;

public class Enemy
{
    boolean alive = true;
    int health, armor, damage, range, attackSpeed;
    Map map;
    Location location;
    Bird target;
    private int cooldown = attackSpeed;
    private ArrayList<Bird> birdsInRange = new ArrayList<Bird>();
    public Enemy(int health, int armor, Location location, int damage, int range, int attackSpeed)
    {
        this.health = health;
        this.armor = armor;
        this.location = location;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
    }
    public int getHealth()
    {
        return health;
    }
    public int getArmor()
    {
        return armor;
    }
    public Location getLocation()
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
        if(cooldown <= 0)
        {
            attack(target); //find nearest bird
            cooldown = attackSpeed;
        }
    }
    public void attack(Bird bird)
    {
        target.takeDamage(damage);
        if(target.getHealth()<=0)
            updateBirdsInRange();
    }
    public void setBirdsInRange()
    {
        ArrayList<Bird> birds = map.getBirds();
        for(Bird bird: birds)
        {
            if(bird.getLocation().distanceFrom(this.location) <= range)
                birdsInRange.add(bird);
        }
    }
    public void updateBirdsInRange()
    {
        if(birdsInRange.size()<1)
            target = null;
        else
        {
            birdsInRange.remove(target);
            setTarget();
        }
    }
    public void setTarget()
    {
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
    }

}