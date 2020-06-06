public class Player {
    private Map map;
    private Nest nest;
    private int money = 0;
    public Player(Map map, Nest nest){
        this.map = map;
        this.nest = nest;
    }
    public Map getMap() { return map; }
    public Nest getNest() { return nest; }
    public int getMoney() { return money; }

}
