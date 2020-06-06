import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Runner extends JPanel implements ActionListener{
    JFrame frame;
    JPanel startPanel;
    JPanel gamePanel;
    JButton startButton = new JButton("Start Game");
    Map map = new Map();
    Nest nest;
    Timer enemySpawnTimer;

    public Runner() {
        frame = new JFrame("Bird Game");
        startPanel = new JPanel();
        startButton.addActionListener(this);
        nest = new Nest(map, new Vector(500, 400));
        enemySpawnTimer = new Timer();
        enemySpawnTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                creatingRandomEnemies();
                repaint();
            }
            public void creatingRandomEnemies(){
                int randomSpawn = (int)((Math.random()*100)+1);
                RandomInRanges randomXRanges = new RandomInRanges(51,949);
                RandomInRanges randomYRanges = new RandomInRanges(0,0);
                randomYRanges.addRange(800,800);
                randomXRanges.addRange(0, 100);
                randomXRanges.addRange(900, 1000);
                int randomX = randomXRanges.getRandom();
                int randomY = 0;
                if(randomX < 100 || randomX > 900){
                    randomY = (int)(Math.random() * frame.getHeight());
                    randomX = randomX < 100 ? 0 : frame.getWidth();
                } 
                else randomY = randomYRanges.getRandom();
                if(randomSpawn == 50)
                    map.getEnemies().add(new Enemy(100, 2, new Vector(randomX, randomY), 3, 3, 3, 3, 3));
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
    
    public static void main(String[] args) {
        new Runner();
    }
    class RandomInRanges{
        private ArrayList<Integer> range = new ArrayList<>();
        RandomInRanges(int min, int max)
        {
            this.addRange(min, max);
        }
        final void addRange(int min, int max)
        {
            for(int i = min; i <= max; i++)
            {
                this.range.add(i);
            }
        }
        int getRandom(){  return this.range.get(new Random().nextInt(this.range.size())); }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            frame.remove(startPanel);
            frame.add(gamePanel);
        }
    }
}