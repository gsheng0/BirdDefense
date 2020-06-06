public class Nest {
    Map map;
    Vector vector;
    int health;
    int armor;
    boolean alive;
    public Nest(Map map, Vector vector){
        this.map = map;
        this.vector = vector;
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
    public Vector getVector(){ return this.vector; }
    public void setAlive(){
        this.alive = health > 0 ? true : false;
    }

}