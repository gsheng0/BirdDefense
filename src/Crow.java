public class Crow extends Bird{
    public Crow(Map map, Vector location){
        super(map, 150, 5, location, 15, 66, 100, 115, Util.CROW);
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
            map.add(newest);
            if(map.player.getMoney() <= 200)
                map.player.removeMoney(200);
            return newest;
        }
    }
}
