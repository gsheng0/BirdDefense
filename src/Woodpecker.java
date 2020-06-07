public class Woodpecker extends Bird{
    public Woodpecker(Map map, Vector location){
        super(map, 250, 5, location, 13, 10, 25, 10, Util.WOODPECKER);
    }
    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map;}
        public static Woodpecker build(Vector location){
            Woodpecker newest = new Woodpecker(map, location);
            if(map.player.getMoney() <= 50)
                map.add(newest);
            map.player.removeMoney(50);
            return newest;
        }
    }
}
