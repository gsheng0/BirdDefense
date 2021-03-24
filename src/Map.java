import java.util.*;
public class Map{
    //container for everything within one map
    //an "instance" of the game
    private ArrayList<Bird> birds = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<Projectile> shouldRemove = new ArrayList<>();
    private Nest nest;
    Player player;
    public Map(){

    }
    public void setNest(Nest nest){ this.nest = nest; }
    public void simulate(){
        enemies.forEach(Enemy::exist);
        birds.forEach(Bird::exist);
        projectiles.forEach(Projectile::exist);

    }
    public void add(Enemy en){
        enemies.add(en);
    }
    public void add(Bird bird){
        birds.add(bird);
    }
    public void add(Projectile proj){
        projectiles.add(proj);
    }
    public void clear(){
        birds = new ArrayList<>();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        shouldRemove = new ArrayList<>();
    }
    public Nest getNest() { return nest; }
    public ArrayList<Enemy> getEnemies(){ return enemies; }
    public ArrayList<Bird> getBirds() { return birds; }
    public ArrayList<Projectile> getProjectiles() { return projectiles; }
    public void remove(Entity en){
        if(en instanceof Nest){
            //lose
        }
        else if(en instanceof Bird){
            birds.remove(en);
        }
    }
    public void remove(Enemy enemy){
        enemies.remove(enemy);
    }
    public void remove(Projectile proj){
        projectiles.remove(proj);
    }
}