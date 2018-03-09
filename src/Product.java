public  class  Product 
{
  public Product(int[][] d, int prix)
  {
    setDemand(d);
    salePrice = prix;
    amount = 0;
  }
  
  private int amount;
  public void addAmount(int j)
  {
    amount +=j;
  }
  public int getAmount()
  {
    return amount;
  }

  private int salePrice;
  public int getPrice()
  {
    return salePrice;
  }
  
  private int[][] demand = new int[6][4];
  public void setDemand(int[][] d)
  {
    demand = d;
  }
  public int getDemand(int term, int grade)
  {
    return demand[term][grade];
  }
}