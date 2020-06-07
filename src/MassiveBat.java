public class MassiveBat extends Enemy{
    public MassiveBat(Map map, Vector location){
        super(map, 2500, 4, location, 99, 250, 250, 0.12, 40, Util.MASSIVE_BAT);
    }

    @Override
    public void takeDamage(int damageGiven) {
        super.takeDamage(damageGiven);
        if(this.getHealth() <= 0)
        {
            for(int i = 0; i < 15; i++)
                Bat.Factory.build(new Vector(this.getCenter().x + (int)(Math.random() * 41) - 20, this.getCenter().x + (int)(Math.random() * 41) - 20));
        }
    }

    public static class Factory{
        private static Map map;
        public static void setMap(Map map) { Factory.map = map; }
        public static MassiveBat build(Vector location){
            MassiveBat newest = new MassiveBat(map, location);
            map.add(newest);
            return newest;
        }
    }
}
