import java.util.ArrayList;
public class Bird
{
    private Map map;
    private int health, armor, damage, range, attackSpeed;
    private Vector vector;
    private Enemy target;
    private ArrayList<Enemy> enemiesInRange = new ArrayList<>();
    private int cooldown;
    private boolean alive = true;
    public Bird(Map map, int health, int armor, Vector vector, int damage, int range, int attackSpeed)
    {
        this.map = map;
        this.health = health;
        this.armor = armor;
        this.vector = vector;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
    }
    public int getHealth() { return health; }
    public int getArmor() { return armor;}
    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackSpeed() { return attackSpeed; }
    public Vector getVector() { return vector; }
    public Map getMap() { return map; }
    public void attack(Enemy en){
        en.takeDamage(this.damage);
        if(en.getHealth() <= 0)
            updateTarget();
    }
    public void updateEnemiesInRange(){
        enemiesInRange = new ArrayList<Enemy>();
        ArrayList<Enemy> enemies = map.getEnemies();
        for(Enemy enemy : enemies){
            if(enemy.getVector().distanceFrom(this.vector) <= range)
                enemiesInRange.add(enemy);
        }
    }
    public void updateTarget(){
        if(enemiesInRange.size() < 1)
            target = null;
        double closest = 10000000000.0;
        for(Enemy enemy : enemiesInRange){
            double distance = enemy.getVector().distanceFrom(this.vector);
            if(distance < closest)
            {
                target = enemy;
                closest = distance;
            }
        }
    }
    public void exist(){
        if(cooldown <= 0){
            if(target != null){
                this.attack(target);
                cooldown = attackSpeed;
            }
        }
        else if(cooldown > 0)
            cooldown--;
    }
    public void takeDamage(int damage){
        int newDamage = damage - this.armor;
        this.health -= newDamage;
        if(this.health <= 0) {
            map.getBirds().remove(this);
            alive = false;
        }
    }
    public boolean isAlive() { return alive; }

}