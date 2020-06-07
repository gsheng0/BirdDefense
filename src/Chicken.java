public class Chicken extends Bird {
    public Chicken(Map map, Vector location){
        super(map, 100, 2, location, 15, 33, 150, 25, Util.CHICKEN);
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
