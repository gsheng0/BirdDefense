import java.awt.*;
import java.util.ArrayList;

public class MassiveEgg extends Projectile{
    private int counter = 0;
    public MassiveEgg(Map map, Vector location, Vector stepVector){
        super(map, location, stepVector, 150, 30, Util.MASSIVE_EGG);
    }

    @Override
    public void draw(Graphics g) {
        counter++;
        g.drawImage(Util.rotateDegrees(Util.MASSIVE_EGG, (int)(counter * 0.3)), (int)getLocation().x, (int)getLocation().y, null);
    }

    @Override
    public void exist() {
        ArrayList<Enemy> inContact = new ArrayList<>();
        for(Enemy en : getMap().getEnemies()){
            if(this.inContact(en))
                inContact.add(en);
        }

        this.getLocation().x += getStepVector().x;
        this.getLocation().y += getStepVector().y;
        inContact.forEach(enemy -> enemy.takeDamage(getDamage()));
        if((!Util.withinBounds((int)this.getLocation().x, 0, 1000)) || (!Util.withinBounds((int)this.getLocation().y, 0, 800)))
            getMap().shouldRemove.add(this);
    }
}
