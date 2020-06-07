public class Woodpecker extends Bird{
    public Woodpecker(Map map, Vector location){
        super(map, 250, 5, location, 13, 9, 25, 10, Util.WOODPECKER);
    }
    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map;}
        public static Chicken build(Vector location){
            Chicken newest = new Chicken(map, location);
            map.add(newest);
            return newest;
        }
    }
}
