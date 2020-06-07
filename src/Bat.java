import java.awt.image.BufferedImage;

public class Bat extends Enemy{
    public Bat(Map map, Vector location){
        super(map, 100, 2, location, 3, 150, 25, 0.25, 8, Util.BAT);
    }
    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map; }
        public static Bat build(Vector location){
            Bat newest = new Bat(map, location);
            map.add(newest);
            return newest;
        }
    }
}
