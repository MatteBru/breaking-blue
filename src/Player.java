import java.util.*;

public class Player 
{
  private final int[][] dipDemand = new int[][] {
    {10,20,30,40},
    {10,20,30,40},
    {10,20,30,40},
    {10,20,30,40},
    {10,20,30,40},
    {10,20,30,40}
  };
  private final int[][] vodkaDemand = new int[][] {
    {20,40,60,80},
    {10,20,30,40},
    {30,50,70,90},
    {10,20,30,40},
    {30,50,70,100},
    {10,20,30,40}
  };
  private final int[][] weedDemand = new int[][] {
    {5,10,15,20},
    {2,5,7,10},
    {7,15,25,30},
    {4,8,12,15},
    {7,15,25,35},
    {4,8,12,20}
  };
  private final int[][] addyDemand = new int[][] {
    {2,5,10,15},
    {6,15,30,40},
    {2,5,10,15},
    {6,15,30,30},
    {2,5,10,10},
    {6,15,40,5}
  };
  private final int[][] cokeDemand = new int[][] {
    {0,0,1,2},
    {0,0,0,1},
    {0,0,1,2},
    {0,0,0,1},
    {0,0,1,2},
    {0,0,0,1}
  };
  private int money;
  public void addMoney(int i)
  {
    money += i;
  }
  public int getMoney()
  {
    return money;
  }
  private double rep;
  public double getRep()
  {
    return rep;
  }
  public void addRep(double i)
  {
    if(rep + i < 0)
      rep = 0;
    else if(rep + i > 100)
      rep = 100;
    else
      rep += i;
  }
  private double risk;

  public double getRisk()
  {
    return risk;
  }

  public void addRisk(double i)
  {
    if(risk + i < 0)
      risk = 0;
    else if(risk + i > 1)
      risk = 1;
    else
      risk += i;
  }
  private ArrayList<Product> supply = new ArrayList<Product>();

  public void addStock(int item, int amount)
  {
    supply.get(item).addAmount(amount);
  }
  public Product getProduct(int i)
  {
    return supply.get(i);
  }
  private boolean isOnPro = false;
  public boolean proStatus()
  {
    return isOnPro;
  }
  public void putOnPro()
  {
    isOnPro = true;
    risk += .05;
    for (int d = 0; d < STOCKS.length; d++)
      supply.get(d).addAmount(-supply.get(d).getAmount());
  }

  public boolean canBuyOrNah(int d)
  {
    switch (d) {
      case 0:
        return true;
      case 1:
        return hasID;
      case 2:
        return canBuyWeed;
      case 3:
        return canBuyAddy;
      case 4:
        return canBuyCoke;
    }
    return false;
  }

  private boolean hasID = false;
  public boolean idOrNah()
  {
    return hasID;
  }
  public void getID()
  {
    hasID = true;
  }

  private boolean idOnTheWay;

  public void setIDOnTheWay()
  {
    idOnTheWay = !idOnTheWay;
  }

  public boolean idInTheMail()
  {
    return idOnTheWay;
  }

  private boolean canBuyWeed = false;

  public void getWeed()
  {
    canBuyWeed = true;
  }
  private boolean canBuyAddy = false;

  public void getAddy()
  {
    canBuyAddy = true;
  }
  private boolean canGetAddy = false;
  public boolean scripornah()
  {
    return canGetAddy;
  }
  public void getScrip()
  {
    canGetAddy = true;
  }
  private boolean canBuyCoke = false;

  public void getCoke()
  {
    canBuyCoke = true;
  }
  private double gpa;
  public double getGPA()
  {
    return gpa;
  }
  public void addGPA(double i)
  {
    if (gpa + i > 6)
      gpa = 6.0;
    else
      gpa += i;
  }
  public static final String[] STOCKS = {"Dip", "Vodka" , "Weed" , "Addy", "Coke"};
  public static final String[] STOCKS1 = {"Tobacco", "Alcohol" , "Pot" , "Adderall", "Cocaine"};
  private int character;
  public static final String[] CHARACTERS = {"Kenny Rolando", "John Gerald Fitzhenry", "Abe Braunsberg", "Paulie Salinger"};
  public Player(int j)
  { 
    character = j;
    Product dip = new Product(dipDemand, 15);
    supply.add(dip);
    Product vodka = new Product(vodkaDemand, 15);
    supply.add(vodka);
    Product weed = new Product(weedDemand, 10);
    supply.add(weed);
    Product addy = new Product(addyDemand, 10);
    supply.add(addy);
    Product coke = new Product(cokeDemand, 200);
    supply.add(coke);
    
    switch (character) {
      case 0: {
        money = 50;
        rep = 30;
        risk = 0.0;
        gpa = 3.5;
        getWeed();
        getID();
        break;    
      }
      case 1: {
        money = 150;
        rep = 15;
        risk = 0.0;
        gpa = 4.8;
        break;
      } 
      case 2: {
        money = 200;
        rep = 10;
        risk = 0.2;
        gpa = 4.6;
        break;
      }
      case 3: {
        money = 400;
        rep = 20;
        risk = 0.01;
        gpa = 5.2;
        getID();
        getScrip();
        break;
      }
    }
  }
}