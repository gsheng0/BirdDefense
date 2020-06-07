public class MassiveChicken extends Bird {
    public MassiveChicken(Map map, Vector location){
        super(map, 2500, 5, location, 60, 200, 350, 900, Util.MASSIVE_CHICKEN);
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
            map.add(newest);
            return newest;
        }
    }
}
