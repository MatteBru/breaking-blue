public class Sell 
{
  public static final double[] MARKUPS = {4.0,2.5,1.5,1};  
  public static final double productRepRatio = 5.0;
  
  public static boolean sell(int product, int grade, int quantity, Player p, Sota sota, int[][] currentDemand)
  {
    if(p.getProduct(product).getAmount() >= quantity && currentDemand[grade][product] >= quantity) {
      p.addStock(product, -quantity);
      p.addMoney((int)(quantity*p.getProduct(product).getPrice()*MARKUPS[grade]));
      p.addRep(quantity/productRepRatio);
      p.addRisk(MARKUPS[grade]*quantity/1000);
      p.addGPA(-quantity/200.0);
      if (sota.isFinalsWeek())
        p.addGPA(-quantity/200.0);      
      currentDemand[grade][product] -= quantity;
      return true;
    }
    return false;
  }
}
