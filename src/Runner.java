import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class Runner extends JPanel{
    JFrame frame;
    CardLayout cardLayout = new CardLayout();
    JPanel menuPanel, mainPanel;
    GamePanel gamePanel;
    JButton startButton = new JButton("Start Game");
    Map map = new Map();
    Nest nest;
    Timer enemySpawnTimer;
    int counter = 0;
    Player player;

    public Runner() {
        MouseComboListener.getInstance().setMap(map);
        frame = new JFrame("Bird Game");
        MouseComboListener.getInstance().frame = frame;
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
    class GamePanel extends JPanel{
        public GamePanel(){
            add(new JLabel("Game"));
            nest = new Nest(map, new Vector(350, 250));
            map.setNest(nest);
            Chicken.Factory.setMap(map);
            Bat.Factory.setMap(map);
            MassiveBat.Factory.setMap(map);
            MassiveChicken.Factory.setMap(map);
            player = new Player(map, nest);
            MassiveBat.Factory.build(new Vector(0, 0));
        }
        public Dimension getPreferredSize() {
            return new Dimension(1000, 800);
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if((int)(Math.random() * 40) == 0)
                spawnEnemy();

            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(120,241,255);
            Color color2 = color1.darker();
            GradientPaint gp = new GradientPaint(
                0, 0, color1, 0, 800, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, 1000, 800);

            map.simulate();
            map.getNest().draw(g);
            map.getProjectiles().forEach(projectile -> projectile.draw(g));
            map.getEnemies().forEach(enemy -> enemy.draw(g));
            map.getBirds().forEach(bird -> bird.draw(g));
            g.setColor(new Color(40, 26, 13).brighter().brighter().brighter());
            g.fillRect(1000, 0, 400, 800);
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.BLACK);
            g2d.drawRect(1020, 60, 360, 205);
            g2d.drawRect(1050, 95, 125, 135);
            g2d.drawRect(1225, 95, 125, 135);
            g2d.drawImage(Util.CHICKEN_RESIZE, 1050 + 20, 95 + 25, null);
            g2d.drawImage(Util.MASSIVE_CHICKEN_RESIZE, 1225, 95 + 5, null);

            g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            g.setColor(Color.BLACK);
            g.drawString("Money: " + player.getMoney(), 1015, 25);
            g.drawString("Health: " + player.getNest().getHealth(), 1015, 45);

            if(MouseComboListener.getInstance().selection != MouseComboListener.Selection.none) {
                if (MouseComboListener.getInstance().selection == MouseComboListener.Selection.chicken)
                    g.drawImage(Util.CHICKEN, (int)MouseComboListener.getInstance().location.x - 25, (int)MouseComboListener.getInstance().location.y - 25, null);

                else if(MouseComboListener.getInstance().selection == MouseComboListener.Selection.massive_chicken){
                    g.drawImage(Util.MASSIVE_CHICKEN, (int)MouseComboListener.getInstance().location.x - 25, (int)MouseComboListener.getInstance().location.y - 25, null);
                }
            }
            for(Projectile proj : map.shouldRemove)
                map.getProjectiles().remove(proj);
            repaint();
        }
        public void spawnEnemy(){
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
    public static class MouseComboListener implements MouseMotionListener, MouseListener{
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
        public void mousePressed(MouseEvent e) {
            if(Util.withinBounds(e.getX(), 1050, 1175) && Util.withinBounds(e.getY(), 95, 230))
                selection = Selection.chicken;

            else if(Util.withinBounds(e.getX(), 1225, 1350) && Util.withinBounds(e.getY(), 95, 230))
                selection = Selection.massive_chicken;


            System.out.println(frame.getSize());
            //map.add(new Enemy(map ,100, 200, new Vector(e.getPoint()), 3, 150, 3, 3, 20));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(Util.withinBounds(e.getX(), 0, 1000) && Util.withinBounds(e.getY(), 0, 800))
            {
                if(selection == Selection.chicken)
                    Chicken.Factory.build(new Vector(e.getPoint().x - 25, e.getPoint().y - 25));
                else if(selection == Selection.massive_chicken)
                    MassiveChicken.Factory.build(new Vector(e.getPoint().x - 25, e.getPoint().y - 25));
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
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            location = new Vector(e.getPoint());
        }
    }
}