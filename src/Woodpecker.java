public class Woodpecker extends Bird {
    public static final int MAX_HEALTH = 250;
    public static final int ARMOR = 5;
    public static final int SIZE = 13;
    public static final int DAMAGE = 10;
    public static final int RANGE = 25;
    public static final int ATTACK_SPEED = 10;

    public Woodpecker(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, SIZE, DAMAGE, RANGE, ATTACK_SPEED, Util.WOODPECKER);
    }
    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map;}
        public static Woodpecker build(Vector location){
            Woodpecker newest = new Woodpecker(map, location);
            if(map.player.getMoney() >= 50) {
                map.add(newest);
                map.player.removeMoney(50);
            }
            return newest;
        }
    }
}
