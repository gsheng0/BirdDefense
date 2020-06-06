public class Nest {
    Map map;
    Location location;
    int health;
    int armor;
    boolean alive;
    public Nest(Map map, Location location){
        this.map = map;
        this.location = location;
        this.health = 1000;
        this.armor = 3;
        this.alive = true;
    }
    public int getArmor(){ return armor; }
    public int getHealth(){ return health; }
    public boolean getAlive(){ return alive; }
    public boolean doesExist(){ return this == null; }
    public void setHealth(int health){
        this.health = health;
        setAlive();
    }
    public Location getLocation(){ return this.location; }
    public void setAlive(){
        this.alive = health > 0 ? true : false;
    }

}