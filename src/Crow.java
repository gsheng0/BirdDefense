public class Crow extends Bird {
    public static final int MAX_HEALTH = 150;
    public static final int ARMOR = 5;
    public static final int SIZE = 15;
    public static final int DAMAGE = 66;
    public static final int RANGE = 100;
    public static final int ATTACK_SPEED = 115;
    public Crow(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, SIZE, DAMAGE, RANGE, ATTACK_SPEED, Util.CROW);
    }

    @Override
    public void attack(Enemy en) {
        double distance = this.getCenter().distanceFrom(en.getCenter());
        double time = distance / 0.5;
        double xDistance = en.getCenter().x - this.getCenter().x;
        double yDistance = en.getCenter().y - this.getCenter().y;
        Rock rock = new Rock(getMap(), this.getCenter(), new Vector(xDistance / time, yDistance/time));
        getMap().add(rock);
    }
    public static class Factory{
        private static Map map;
        public static void setMap(Map map){ Crow.Factory.map = map;
        }
        public static Crow build(Vector location){
            Crow newest = new Crow(map, location);

            if(map.player.getMoney() >= 200) {
                map.player.removeMoney(200);
                map.add(newest);
            }
            return newest;
        }
    }
}
