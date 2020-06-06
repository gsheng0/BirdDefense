public class Nest {
    Map map;
    Vector location;
    int health;
    int armor;
    boolean alive;
    public Nest(Map map, Vector location){
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
    public Vector getLocation(){ return this.location; }
    public void setAlive(){
        this.alive = health > 0 ? true : false;
    }

}