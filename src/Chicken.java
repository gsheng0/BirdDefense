public class Chicken extends Bird {
    public Chicken(Map map, Vector location){
        super(map, 100, 2, location, 15, 33, 150, 60, Util.CHICKEN);
    }

    @Override
    public void attack(Enemy en) {
        double distance = this.getCenter().distanceFrom(en.getCenter());
        double time = distance;
        double xDistance = en.getCenter().x - this.getCenter().x;
        double yDistance = en.getCenter().y - this.getCenter().y;
        Egg egg = new Egg(getMap(), this.getCenter(), new Vector(xDistance / ((double)time), yDistance / ((double)time)));
        getMap().add(egg);
    }

    public static class Factory{
        private static Map map;
        public static void setMap(Map map){
            Factory.map = map;
        }
        public static Chicken build(Vector location){
            Chicken newest = new Chicken(map, location);
            map.add(newest);
            return newest;
        }
    }
}
