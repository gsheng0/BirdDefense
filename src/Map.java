import java.util.*;
public class Map{
    public ArrayList<Bird> birdList = new ArrayList<>();
    public ArrayList<Enemy> enemyList = new ArrayList<>();
    Nest nest;
    public Map(){

    }
    public void simulate(){
        enemyList.stream().forEach(Enemy::exist);
        birdList.stream().forEach(Bird::exist);
    }

    public ArrayList<Enemy> getEnemies(){ return enemyList; }
    public ArrayList<Bird> getBirds() { return birdList; }
    public void remove(Entity en){
        if(en instanceof Nest){
            //lose
        }
        else if(en instanceof Bird){
            birdList.remove(en);
        }
    }
}