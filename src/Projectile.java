import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Projectile {
    private Vector location;
    private int damage, radius;
    private Vector stepVector;
    private Map map;
    private BufferedImage image;
    public boolean shouldRemove = false;
    public Projectile(Map map, Vector location, Vector stepVector, int damage, int radius, BufferedImage image){
        this.location = location;
        this.stepVector = stepVector;
        this.damage = damage;
        this.radius = radius;
        this.image = image;
        this.map = map;
    }
    public void draw(Graphics g){
        g.drawImage(image, (int)location.x, (int)location.y, null);
    }
    public Map getMap(){ return map; }
    public int getDamage() { return damage; }
    public int getRadius() { return radius; }
    public Vector getLocation() { return location; }
    public Vector getStepVector() { return stepVector; }
    public Vector getCenter() {
        return new Vector(location.x + radius, location.y + radius);
    }
    public boolean inContact(Enemy en){
        return en.getCenter().distanceFrom(this.getCenter()) - (en.getSize() + this.radius) <= radius;
    }
    public void exist(){
        ArrayList<Enemy> inContact = new ArrayList<>();
        for(Enemy en : map.getEnemies()){
            if(this.inContact(en))
                inContact.add(en);
        }
        if(inContact.size() == 0)
        {
            this.location.x += stepVector.x;
            this.location.y += stepVector.y;
            if((!Util.withinBounds((int)this.getLocation().x, 0, 1000)) || (!Util.withinBounds((int)this.getLocation().y, 0, 800)))
                getMap().shouldRemove.add(this);
        }
        else {
            inContact.forEach(enemy -> enemy.takeDamage(damage));
            map.shouldRemove.add(this);
        }


    }

}
