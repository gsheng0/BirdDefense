import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Runner extends JPanel{
    JFrame frame;
    Map map = new Map();
    Nest nest;
    public Runner() {
        frame = new JFrame("src.Bird Game");
        System.out.println("yo");
        nest = new Nest(map, new Location(500, 400));
        frame.add(this);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println(nest);
        g.drawOval((int)nest.getLocation().getX()-150, (int)nest.getLocation().getY()-150, 300, 300);
    }
    public static void main(String[] args) {
        new Runner();
    }
}