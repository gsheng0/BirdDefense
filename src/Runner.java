import javax.swing.*;
import java.awt.*;

public class Runner extends JPanel{
    JFrame frame;
    Map map = new Map();
    Nest nest;
    public Runner() {
        frame = new JFrame("src.Bird Game");
        System.out.println("yo");
        nest = new Nest(map, new Vector(500, 400));
        frame.add(this);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.simulate();
        System.out.println(nest);
        g.drawOval((int)nest.getVector().getX()-150, (int)nest.getVector().getY()-150, 300, 300);
    }
    public static void main(String[] args) {
        new Runner();
    }
}