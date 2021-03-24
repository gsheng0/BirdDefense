public class MassiveChicken extends Bird {
    public static final int MAX_HEALTH = 2500;
    public static final int ARMOR = 5;
    public static final int SIZE = 60;
    public static final int DAMAGE = 200;
    public static final int RANGE = 400;
    public static final int ATTACK_SPEED = 1000;
    public MassiveChicken(Map map, Vector location){
        super(map, MAX_HEALTH, ARMOR, location, SIZE, DAMAGE, RANGE, ATTACK_SPEED, Util.MASSIVE_CHICKEN);
    }

    @Override
    public void attack(Enemy en) {
        double distance = this.getCenter().distanceFrom(en.getCenter());
        double time = distance / 0.1;
        double xDistance = en.getCenter().x - this.getCenter().x;
        double yDistance = en.getCenter().y - this.getCenter().y;
        MassiveEgg egg = new MassiveEgg(getMap(), this.getCenter(), new Vector(xDistance / ((double)time), yDistance / ((double)time)));
        getMap().add(egg);
    }

    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map; }
        public static MassiveChicken build(Vector location){
            MassiveChicken newest = new MassiveChicken(map, location);
            if(map.player.getMoney() >= 750) {
                map.add(newest);
                map.player.removeMoney(750);
            }
            return newest;
        }
    }
}
