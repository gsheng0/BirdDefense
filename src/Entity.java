public class Entity {
    private Map map;
    private Vector location;
    private int health, armor, size;
    private boolean alive = true;
    public Entity(Map map, int health, int armor, Vector location, int size){
        this.map = map;
        this.location = location;
        this.health = health;
        this.armor = armor;
        this.size = size;
    }
    public Map getMap() { return map; }
    public int getHealth() { return health; }
    public int getArmor() { return armor;}
    public int getSize() { return size; }
    public Vector getLocation() { return location; }
    public boolean isAlive() { return alive; }
    public void takeDamage(int damage){
        int newDamage = damage - this.armor;
        this.health -= newDamage;
        if(this.health <= 0) {
            map.remove(this);
            alive = false;
        }
    }
}

