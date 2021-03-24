public class Bat extends Enemy {
    public static final int MAX_HEALTH = 100;
    public static final int ARMOR = 2;
    public static final int DAMAGE = 3;
    public static final int VISION = 150;
    public static final int ATTACK_SPEED = 85;
    public static final double MOVE_SPEED = 0.25;
    public static final int SIZE = 8;
    public static final int MONEY = 7;
    public Bat(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, DAMAGE, VISION, ATTACK_SPEED, MOVE_SPEED, SIZE, Util.BAT, MONEY);
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
