public class Chicken extends Bird {
    public static final int RANGE = 200;
    public static final int MAX_HEALTH = 100;
    public static final int ARMOR = 2;
    public static final int SIZE = 15;
    public static final int DAMAGE = 33;
    public static final int ATTACK_SPEED = 60;
    public Chicken(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, SIZE, DAMAGE, RANGE, ATTACK_SPEED, Util.CHICKEN);
    }

    @Override
    public void attack(Enemy en) {
        double distance = this.getCenter().distanceFrom(en.getCenter());
        double time = distance / 0.1;
        double xDistance = en.getCenter().x - this.getCenter().x;
        double yDistance = en.getCenter().y - this.getCenter().y;
        Egg egg = new Egg(getMap(), this.getCenter(), new Vector(xDistance / ((double)time), yDistance / ((double)time))); //creates new projectile with step vector based on expected amount of frames to reach target
        //assumes that target is stationary
        getMap().add(egg);
    }

    public static class Factory{ //builder class for chickens
        private static Map map;
        public static void setMap(Map map){
            Factory.map = map;
        }
        public static Chicken build(Vector location){
            Chicken newest = new Chicken(map, location);
            if(map.player.getMoney() >= 100) {
                map.add(newest);
                map.player.removeMoney(100);
            }
            return newest;
        }
    }
}
