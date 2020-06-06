import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.net.URL;

public class Runner extends JPanel{
    JFrame frame;
    CardLayout cardLayout = new CardLayout();
    JPanel menuPanel, mainPanel;
    GamePanel gamePanel;
    JButton startButton = new JButton("Start Game");
    Map map = new Map();
    Nest nest;
    Timer enemySpawnTimer;

    public Runner() {
        frame = new JFrame("Bird Game");
        menuPanel = new JPanel();
        gamePanel = new GamePanel();
        startButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "game");

        });
        menuPanel.add(startButton);
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(gamePanel, "game");
        frame.add(mainPanel);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void createBirds()
    {
        for(int x=0;x<7;x++)
            map.add(new Bird(map, 100, 5, Vector.randomVector(),15,33,150,5));
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
    class GamePanel extends JPanel{
        BufferedImage bat;
        public GamePanel(){
            add(new JLabel("Game"));
            nest = new Nest(map, new Vector(350, 250));
            URL resource = getClass().getResource("bat.png");
            try {
                bat = ImageIO.read(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.setNest(nest);
            createBirds();
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
                        map.add(new Enemy(map ,100, 2, new Vector(randomX, randomY), 3, 150, 3, 3, 8));
                }
            }, 0, 20);  
        }
        public Dimension getPreferredSize() {
            return new Dimension(1000, 800);
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            map.simulate();
            g.drawOval((int)nest.getLocation().getX(), (int)nest.getLocation().getY(), 300, 300);
            g.setColor(Color.RED);
            for(Enemy enemy : map.getEnemies()){
                //g.fillOval((int)enemy.getLocation().x, (int)enemy.getLocation().y, enemy.getSize() * 2, enemy.getSize() * 2);
                g.drawImage(bat, (int)enemy.getLocation().x, (int)enemy.getLocation().y, null);
            }
            g.setColor(Color.CYAN);
            for(Bird bird: map.getBirds())
                g.fillOval((int)bird.getLocation().getX(), (int) bird.getLocation().getY(), bird.getSize() * 2, bird.getSize() * 2);
        }
    }
}