import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.*;
import java.util.Timer;

public class Runner extends JPanel{
    JFrame frame;
    CardLayout cardLayout = new CardLayout();
    JPanel menuPanel, mainPanel, endPanel;
    GamePanel gamePanel;
    Map map = new Map();
    Nest nest;
    Timer enemySpawnTimer;
    int counter = 0;
    int ramp = 10000;
    int countTimer = 0;
    Player player;


    public Runner() {
        MouseComboListener.getInstance().setMap(map);
        frame = new JFrame("Bird Game");
        MouseComboListener.getInstance().frame = frame;
        menuPanel = new MenuPanel();
        endPanel = new EndPanel();
        gamePanel = new GamePanel();


        mainPanel = new JPanel(cardLayout);
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(gamePanel, "game");
        mainPanel.add(endPanel, "end");

        frame.add(mainPanel);
        frame.addMouseListener(MouseComboListener.getInstance());
        frame.addMouseMotionListener(MouseComboListener.getInstance());
        frame.setSize(1400, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
    class MenuPanel extends JPanel{ //Start menu
        Color color1 = new Color(204, 153, 255);
        Color color2 = new Color(193,240,240);
        GradientPaint gp = new GradientPaint(0,0,color1,1400,800,color2);
        JButton startButton = new JButton("Start Game");
        JLabel startText = new JLabel("BIRD DEFENSE! Create birds and defend your nest!");
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints gbc2 = new GridBagConstraints();

        public MenuPanel(){
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc2.gridwidth = GridBagConstraints.REMAINDER;
            gbc2.anchor = GridBagConstraints.SOUTH;
            setLayout(new GridBagLayout());
            gbc.insets = new Insets(-30,-30,-30,-30);
            add(startText, gbc);
            add(startButton, gbc2);
            startButton.addActionListener(e -> {
                gamePanel = new GamePanel();
                map.clear();
                cardLayout.show(mainPanel, "game");
            });
            startButton.setIcon(new ImageIcon(Util.BAT));
            startButton.setOpaque(false);
            startButton.setContentAreaFilled(false);
            startButton.setBorderPainted(false);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1400, 800);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setPaint(gp);
            g2d.fillRect(0,0,1400,800);
            g2d.drawImage(Util.BIRD_DEFENSE, 700-Util.BIRD_DEFENSE.getWidth()/2,300-Util.BIRD_DEFENSE.getHeight()/2,null);
        }
    }
    class EndPanel extends JPanel{ //Gameover Screen
        Color color1 = new Color(204, 153, 255);
        Color color2 = new Color(193,240,240);
        GradientPaint gp = new GradientPaint(0,0,color1,1400,800,color2);
        GridBagConstraints gbc = new GridBagConstraints();
        JButton restartButton = new JButton("Restart");
        public EndPanel(){
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.anchor = GridBagConstraints.CENTER;
            setLayout(new GridBagLayout());
            add(restartButton, gbc);
            restartButton.addActionListener(e -> {
                gamePanel = new GamePanel();
                map.clear(); //clears map
                cardLayout.show(mainPanel, "game");
                countTimer = 0; //resets ramp up
            });
            restartButton.setIcon(new ImageIcon(Util.BAT));
            restartButton.setOpaque(false);
            restartButton.setContentAreaFilled(false);
            restartButton.setBorderPainted(false);

        }
        public Dimension getPreferredSize() {
            return new Dimension(1400, 800);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setPaint(gp);
            g2d.fillRect(0,0,1400,800);
            g2d.drawImage(Util.END_GAME, 700-Util.END_GAME.getWidth()/2,300-Util.END_GAME.getHeight()/2,null);
        }
    }
    class GamePanel extends JPanel{
        public GamePanel(){
            //setting up map and passing in parameters for factory classes for entities
            nest = new Nest(map, new Vector(350, 250));
            map.setNest(nest);
            Chicken.Factory.setMap(map);
            Bat.Factory.setMap(map);
            MassiveBat.Factory.setMap(map);
            MassiveChicken.Factory.setMap(map);
            Woodpecker.Factory.setMap(map);
            Crow.Factory.setMap(map);
            player = new Player(map, nest);
            MassiveBat.Factory.build(new Vector(0, 0));
        }
        public Dimension getPreferredSize() {
            return new Dimension(1000, 800);
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 1000, 800);
            if(nest.getHealth() <= 0) //ends game if health goes below 1
                cardLayout.show(mainPanel, "end");
            if(((int)(Math.random() * ((int)Math.max(40,(ramp - countTimer/60)))) == 0)) { //spawns random enemy
                //becomes more likely as timer becomes larger and larger until countTimer == ramp
                spawnEnemy();
            }
            //drawing map
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(120,241,255);
            Color color2 = color1.darker();
            GradientPaint gp = new GradientPaint(
                    0, 0, color1, 0, 800, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, 1000, 800);

            map.simulate(); //calls exist method for all entities once

            map.getNest().draw(g);
            //drawing birds, enemies, and projectiles
            map.getProjectiles().forEach(projectile -> projectile.draw(g));
            map.getEnemies().forEach(enemy -> enemy.draw(g));
            map.getBirds().forEach(bird -> bird.draw(g));

            //drawing side bar
            g.setColor(new Color(40, 26, 13).brighter().brighter().brighter());
            g.fillRect(1000, 0, 400, 800);
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);

            g2d.drawRect(1020, 60, 360, 205);
            g2d.drawRect(1050, 95, 125, 135);
            g2d.drawRect(1225, 95, 125, 135);
            g2d.drawImage(Util.CHICKEN_RESIZE, 1050 + 20, 95 + 25, null);
            g2d.drawImage(Util.MASSIVE_CHICKEN_RESIZE, 1225, 95 + 5, null);
            g.drawString("$100", 1050+(125/2)-5, 90);
            g.drawString("$750", 1225+(125/2)-5, 90);


            g2d.drawRect(1020, 290, 360, 205);
            g2d.drawRect(1050, 325, 125, 135);
            g2d.drawRect(1225, 325, 125, 135);
            g2d.drawImage(Util.WOODPECKER_RESIZE, 1050 + 20, 325 + 25, null);
            g2d.drawImage(Util.CROW_RESIZE, 1225 + 20, 325 + 25, null);
            g.drawString("$50", 1050+(125/2)-5, 320);
            g.drawString("$200", 1225+(125/2)-5, 320);



            g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            g.setColor(Color.BLACK);
            g.drawString("Money: " + player.getMoney(), 1015, 25);
            g.drawString("Health: " + player.getNest().getHealth(), 1015, 45);


            //draw tower if player is drag and dropping a tower selection
            //also draws circle to show range of the selected tower
            if(MouseComboListener.getInstance().selection != MouseComboListener.Selection.none) {
                int mouseX = (int)MouseComboListener.getInstance().location.x - 25;
                int mouseY = (int)MouseComboListener.getInstance().location.y - 25;
                if (MouseComboListener.getInstance().selection == MouseComboListener.Selection.chicken) {
                    g.drawImage(Util.CHICKEN, mouseX, mouseY, null);
                    g.drawOval(mouseX - Chicken.RANGE + 25, mouseY - Chicken.RANGE + 25, Chicken.RANGE * 2, Chicken.RANGE * 2);
                }
                else if(MouseComboListener.getInstance().selection == MouseComboListener.Selection.massive_chicken){
                    g.drawImage(Util.MASSIVE_CHICKEN, mouseX, mouseY, null);
                    g.drawOval(mouseX - MassiveChicken.RANGE + Util.MASSIVE_CHICKEN.getHeight()/2, mouseY - MassiveChicken.RANGE + Util.MASSIVE_CHICKEN.getHeight()/2, MassiveChicken.RANGE * 2, MassiveChicken.RANGE * 2);
                }
                else if(MouseComboListener.getInstance().selection == MouseComboListener.Selection.woodpecker){
                    g.drawImage(Util.WOODPECKER, mouseX, mouseY, null);
                    g.drawOval(mouseX - Woodpecker.RANGE + 25, mouseY - Woodpecker.RANGE + 25, Woodpecker.RANGE * 2, Woodpecker.RANGE * 2);
                }
                else if(MouseComboListener.getInstance().selection == MouseComboListener.Selection.crow){
                    g.drawImage(Util.CROW, mouseX, mouseY, null);
                    g.drawOval(mouseX - Crow.RANGE + 25, mouseY - Crow.RANGE + 25, Crow.RANGE * 2, Crow.RANGE * 2);
                }

            }
            //removes offscreen projectiles
            for(Projectile proj : map.shouldRemove)
                map.getProjectiles().remove(proj);

            countTimer++;

            repaint();

        }
        public void spawnEnemy(){ //decides which enemy to spawn
            RandomInRanges randomXRanges = new RandomInRanges(51,949);
            RandomInRanges randomYRanges = new RandomInRanges(0,0);
            randomYRanges.addRange(800,800);
            randomXRanges.addRange(0, 100);
            randomXRanges.addRange(900, 1000);
            int randomX = randomXRanges.getRandom();
            int randomY = 0;
            if(randomX < 100 || randomX > 900){
                randomY = (int)(Math.random() * frame.getHeight());
                randomX = randomX < 100 ? 0 : 1000;
            }
            else randomY = randomYRanges.getRandom();
            Bat.Factory.build(new Vector(randomX, randomY));
            if((int)(Math.random() * 1000) == 0)
                MassiveBat.Factory.build(new Vector(randomX, randomY));
        }
    }
    public static class MouseComboListener implements MouseMotionListener, MouseListener{ //listener class that makes coding drag and drop eaiser
        private Vector center = new Vector(500, 400);
        private static MouseComboListener instance;
        private MouseComboListener(){ }
        private Map map;
        Vector location;
        Selection selection;
        enum Selection{
            chicken,
            none,
            massive_chicken,
            woodpecker,
            crow
        }
        JFrame frame;
        public static MouseComboListener getInstance(){
            if(instance == null)
                instance = new MouseComboListener();
            return instance;
        }
        public void setMap(Map map){
            this.map = map;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) { //if the mouse is pressed within the range of those tiles, the player has selected a tower
            if(Util.withinBounds(new Vector(e.getPoint()), new Vector(1050, 95), new Vector(1175, 230)))
                selection = Selection.chicken;

            else if(Util.withinBounds(new Vector(e.getPoint()), new Vector(1225, 95),new Vector(1350, 230)))
                selection = Selection.massive_chicken;

            else if(Util.withinBounds(new Vector(e.getPoint()), new Vector(1050, 325), new Vector(1175, 460)))
                selection = Selection.woodpecker;

            else if(Util.withinBounds(new Vector(e.getPoint()), new Vector(1225, 95), new Vector(1350, 460)))
                selection = Selection.crow;

            //map.add(new Enemy(map ,100, 200, new Vector(e.getPoint()), 3, 150, 3, 3, 20));
        }

        @Override
        public void mouseReleased(MouseEvent e) { //if the mouse is released on a valid location, place the tower
            //deletes selection if not valid
            if(Util.withinBounds(e.getX(), 0, 1000) && Util.withinBounds(e.getY(), 0, 800))
            {
                Vector buildLocation = new Vector(e.getPoint().x - 25, e.getPoint().y - 25);
                if(selection == Selection.chicken)
                    Chicken.Factory.build(buildLocation);
                else if(selection == Selection.massive_chicken)
                    MassiveChicken.Factory.build(buildLocation);
                else if(selection == Selection.woodpecker)
                    Woodpecker.Factory.build(buildLocation);
                else if(selection == Selection.crow)
                    Crow.Factory.build(buildLocation);
            }
            selection = Selection.none;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            location = new Vector(e.getPoint());
        } //updates mouse location

        @Override
        public void mouseMoved(MouseEvent e) {
            location = new Vector(e.getPoint());
        } //updates mouse location
    }
}