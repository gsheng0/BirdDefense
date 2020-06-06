import java.util.*;

public class Enemy
{
    boolean alive = true;
    int health, armor, damage, range, attackSpeed;
    Map map;
    Vector vector;
    Bird target;
    private int cooldown = 0;
    private int moveSpeed = 0;
    private ArrayList<Bird> birdsInRange = new ArrayList<Bird>();
    public Enemy(int health, int armor, Vector vector, int damage, int range, int attackSpeed, int moveSpeed)
    {
        this.health = health;
        this.armor = armor;
        this.vector = vector;
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
    public Vector getVector()
    {
        return vector;
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
        if(target != null){
            if(birdsInRange.contains(target))
            {
                if(cooldown <= 0)
                {
                    this.attack(target);
                    cooldown = attackSpeed;
                }
            }
        }
    }
    public void moveTowards(Vector vector){
        double distance = this.vector.distanceFrom(vector);
        double time = distance / ((double)moveSpeed);
        
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
            if(bird.getVector().distanceFrom(this.vector) <= range)
                birdsInRange.add(bird);
        }
    }
    public void updateTarget()
    {
        if(birdsInRange.size() < 1)
            target = null;
        double minDistance=Double.MAX_VALUE;
        for(Bird bird: birdsInRange)
        {
            double distance = bird.getVector().distanceFrom(this.vector);
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
        if(health <= 0)
            alive = false;
    }

}