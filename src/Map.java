import java.util.*;
public class Map{
    public ArrayList<Bird> birdList;
    public ArrayList<Enemy> enemyList;
    Nest nest;
    public Map(){

    }
    public void simulate(){
        enemyList.stream().forEach(Enemy::exist);
        birdList.stream().forEach(Bird::exist);
    }
    public ArrayList<Enemy> getEnemies(){ return enemyList; }
    public ArrayList<Bird> getBirds() { return birdList; }
}