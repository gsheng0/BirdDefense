import java.util.ArrayList;
public class Bird extends Entity
{
    private int damage, range, attackSpeed, size;
    private Enemy target;
    private ArrayList<Enemy> enemiesInRange = new ArrayList<>();
    private int cooldown;
    public Bird(Map map, int health, int armor, Vector location, int size, int damage, int range, int attackSpeed)
    {
        super(map, health, armor, location, size);
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
    }

    public int getDamage() { return damage; }
    public int getRange() { return range; }
    public int getAttackSpeed() { return attackSpeed; }
    public void attack(Enemy en){
        System.out.println("Attacked");
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
        System.out.println(enemiesInRange.size());
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
        System.out.println("Bird at: " + getLocation().toString() + " " + target);
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
    }

}