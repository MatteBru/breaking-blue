public class Buy 
{
  public static final int[] COSTS = {7,10,7,5,100};

  public static boolean buy(Player p, int d, int q)
  {
    if(p.getMoney() >= COSTS[d]*q && p.canBuyOrNah(d) && q >= 0) {
      p.addStock(d, q);
      p.addMoney(-1*COSTS[d]*q);
      return true;
    } 
    return false;
  }
}
