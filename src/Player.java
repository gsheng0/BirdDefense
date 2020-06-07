public class Player {
    private Map map;
    private Nest nest;
    private int money = 200;
    public Player(Map map, Nest nest){
        this.map = map;
        this.nest = nest;
        map.player = this;
    }
    public void addMoney(int money) { this.money += money; }
    public void removeMoney(int money) { this.money -= money; }
    public Map getMap() { return map; }
    public Nest getNest() { return nest; }
    public int getMoney() { return money; }

}
