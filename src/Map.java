import java.util.*;
public class Map{
    private ArrayList<Bird> birds = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Nest nest;
    public Map(){

    }
    public void setNest(Nest nest){ this.nest = nest; }
    public void simulate(){
        enemies.stream().forEach(Enemy::exist);
        birds.stream().forEach(Bird::exist);
    }
    public void add(Enemy en){
        enemies.add(en);
    }
    public void add(Bird bird){
        birds.add(bird);
    }
    public Nest getNest() { return nest; }
    public ArrayList<Enemy> getEnemies(){ return enemies; }
    public ArrayList<Bird> getBirds() { return birds; }
    public void remove(Entity en){
        if(en instanceof Nest){
            //lose
        }
        else if(en instanceof Bird){
            birds.remove(en);
        }
    }
    public void remove(Enemy en){
        enemies.remove(en);
    }
}