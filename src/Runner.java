import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Runner extends JPanel{
    JFrame frame;
    Map map = new Map();
    Nest nest;
    Timer enemySpawnTimer;

    public Runner() {
        frame = new JFrame("Bird Game");
        nest = new Nest(map, new Vector(500, 400));
        enemySpawnTimer = new Timer();
        enemySpawnTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                int random = (int)((Math.random()*100)+1);
                if(random == 50)
                    map.getEnemies().add(new Enemy(100, 2, new Vector(5, 5), 3, 3, 3, 3));
                repaint();
            }
        }, 0, 20);  
        frame.add(this);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.simulate();
        g.drawOval((int)nest.getLocation().getX()-150, (int)nest.getLocation().getY()-150, 300, 300);
        g.setColor(Color.RED);
        for(Enemy enemy : map.enemyList){
            g.fillOval((int)enemy.location.x, (int)enemy.location.y, 15, 15);
        }
        System.out.println(map.enemyList.size());
    }
    public void handle(InputEvent event){
        System.out.println("yo");

    }
    public static void main(String[] args) {
        new Runner();
    }
}