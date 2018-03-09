import java.util.*;

public class Gameplay
{
  public static final String[] GRADES = {"Frosh","Lowers","Uppers","Seniors"};
  
  public static void main(String[] args)
  {
    Scanner kb = new Scanner(System.in);
    System.out.println("Welcome to BREAKING BLUE, PA's most realistic drug dealing simulator.");
    System.out.println();
    System.out.println("Choose your character:");
    for (int i=0; i<Player.CHARACTERS.length; i++)
      System.out.println(i+1 + " for " + Player.CHARACTERS[i]);
    System.out.println("(All characters appearing in this work are fictitious. Any resemblance to real persons, living or dead, is purely coincidental)");
    int character = kb.nextInt()-1;
    Player p = new Player(character);

    System.out.println();
    System.out.println("Welcome, " + Player.CHARACTERS[character] + ".");
    System.out.println("It is the first day of your senior year.");
    System.out.println("Your goal is make as much $$$ as possible while surviving long enough to graduate.");
    System.out.println("Good luck and godspeed.");
    System.out.println();
    
    Sota time = new Sota();
    Random r = new Random();
    while (time.getWeek() <= 30) {
      System.out.println("Week " + time.getWeek() + " (" + time.getTermName() + ")");
          
      // Update phase
      System.out.println("Your attributes and inventory for the week:");
      displayStats(p);
      displayStocks(p);
      System.out.println();      
      
      // Upgrades phase
      if (p.scripornah()) {
        System.out.println("Your prescription has been filled.");
        p.addStock(3,7);
      }     
      if (p.idInTheMail()) {
        p.getID();
        p.setIDOnTheWay();  
        System.out.println("Your fake has arrived at the mailroom.");
      }
      if (!p.idOrNah() && p.getMoney()>=110) {
        boolean hasAnsWannaFake = false;
        String id = "maybe";
        while (!hasAnsWannaFake) {
          System.out.println("Would you like to buy a fake? ($110)");        
          id = kb.next();
          hasAnsWannaFake = !catchYesNoError(id);
        }
        if (isYes(id)) {
          p.addMoney(-110);
          p.setIDOnTheWay();
          System.out.println("Your fake is on the way.");
        }  
      }    
      
      boolean wannaBuy=false;
      boolean hasAnsWannaBuy=false;
      String buyOrNah = "maybe";
      if (p.getMoney() == 0) {
        System.out.println("You're broke at the moment--no spending this week.");
        hasAnsWannaBuy=true;
      }
        
      while (!hasAnsWannaBuy) {
        System.out.println("Would you like to increase your supply?");
        buyOrNah = kb.next();
        hasAnsWannaBuy = !catchYesNoError(buyOrNah);          
      }      
      if (isYes(buyOrNah))
        wannaBuy=true;
      
      // Buying phase      
      System.out.println();
      if (wannaBuy)
        displayCosts(p);  
      System.out.println();
      while (wannaBuy) {
        boolean isValid = false;
        int d = -1;
        while (!isValid) {
          System.out.println("What do you wanna buy?");
          String prod = kb.next();
          for (int i=0; i<Player.STOCKS.length; i++)
            if (prod.equalsIgnoreCase(Player.STOCKS[i]) || prod.equalsIgnoreCase(Player.STOCKS[i].substring(0,1)) 
            		|| prod.equalsIgnoreCase(Player.STOCKS1[i]))
              d = i;
          if (d==-1)
            System.out.println("Not a valid product.");
          else if (!p.canBuyOrNah(d))
            System.out.println("You don't have the connects to buy that.");
          else
            isValid = true;
        }
        
        boolean hasAnsHowMany = false;
        String quan = "0";
        while (!hasAnsHowMany) {
          System.out.println("How many?");        
          quan = kb.next();
          hasAnsHowMany = isInt(quan);
        } 

        int q = Integer.parseInt(quan);
        if (Buy.buy(p,d,q))
          System.out.println("Transaction successful.");
        else
          System.out.println("You ain't got the cash to do that! Smh");
        
        boolean hasAnsContBuy = false;
        String contBuy = "maybe";
        if (p.getMoney() == 0) {
          System.out.println("You spent the last of your dough. No more buying this week.");
          hasAnsContBuy=true;
          wannaBuy=false;
          System.out.println();
        }          
        while (!hasAnsContBuy) {
          System.out.println("Continue buying?");
          contBuy = kb.next();
          hasAnsContBuy = !catchYesNoError(contBuy);
        }
        if (isNo(contBuy))
          wannaBuy=false;
      }
      
      // Room raid phase
      double totalInRoom = p.getProduct(1).getAmount()+p.getProduct(2).getAmount()+p.getProduct(3).getAmount()*0.2+p.getProduct(4).getAmount()*0.2;
      double foundStuff = Math.random();
      if (foundStuff < totalInRoom/500) {
        System.out.println("The Cluster Dean found the stash in your room.");
        if (p.proStatus() || time.getWeek()>25) {
          System.out.println("You used your one strike already, fam. No DC this time.");
          System.out.println();
          System.out.println("GAME OVER");
          break;
        }
        else {
          System.out.println("The DC has given you probation for possession of contraband.");
          p.putOnPro();
          System.out.println();
        }
      }
      
      // Update phase
      System.out.println("Your updated inventory:");
      displayStocks(p);
      System.out.println();
      
      boolean wannaSell=false;
      boolean hasAnsWannaSell = false;
      String sellOrNah = "maybe";
      if (p.getProduct(0).getAmount()+p.getProduct(1).getAmount()+p.getProduct(2).getAmount()+p.getProduct(3).getAmount()+p.getProduct(4).getAmount()==0) {
        System.out.println("You don't have anything to sell this week.");
        hasAnsWannaSell = true;
      }
      while (!hasAnsWannaSell) {
        System.out.println("Would you like to sell?");
        sellOrNah = kb.next();
        hasAnsWannaSell = !catchYesNoError(sellOrNah);
      }
      if (isYes(sellOrNah))
        wannaSell = true;
            
      // Selling phase      
      System.out.println();
      int[][] howMuchCanSell = {};
      if (wannaSell) {
        // Set demand
        howMuchCanSell = thisWeeksDemand(p,time,r);
        for (int g = 0; g < 4; g++)
          for (int d = 0; d < 5; d++)
            howMuchCanSell[g][d] = (int)(p.getRep()/100*howMuchCanSell[g][d] + 0.5); 
      }
      System.out.println();
      while (wannaSell) {
        boolean isValid = false;
        int d = -1;
        while (!isValid) {
          System.out.println("What do you wanna sell?");
          String prod = kb.next();
          for (int i=0; i<Player.STOCKS.length; i++)
        	if (prod.equalsIgnoreCase(Player.STOCKS[i]) || prod.equalsIgnoreCase(Player.STOCKS[i].substring(0,1)) 
        			|| prod.equalsIgnoreCase(Player.STOCKS1[i]))
              d = i;
          if (d==-1 || p.getProduct(d).getAmount() == 0)
            System.out.println("You don't have any of that.");
          else
            isValid = true;
        }
          
        boolean hasAnsWhatGrade = false;
        String grade = "SEN1OR5";
        int g = -1;
        while (!hasAnsWhatGrade) {
          System.out.println("To what grade?");
          grade = kb.next();
          for (int i=0; i<GRADES.length; i++)
            if (grade.equalsIgnoreCase(GRADES[i]))
              g = i;
          for (int i=9; i<=12; i++)
            if (grade.equals(i+""))
              g = i-9;
          if (g==-1)
            System.out.println("Not a valid answer");
          else
            hasAnsWhatGrade = true;
        }
        
        boolean hasAnsHowMuch = false;
        String amount = "0";
        while (!hasAnsHowMuch) {
          System.out.println("How much?");        
          amount = kb.next();
          hasAnsHowMuch = isInt(amount);
        }
        
        int q = Integer.parseInt(amount);
        if (Sell.sell(d,g,q,p,time,howMuchCanSell))
          System.out.println("Transaction successful.");
        else
          System.out.println("You don't have enough/there isn't enough demand/your rep isn't high enough to sell that much.");
        
        boolean hasAnsContSell = false;
        String contSell = "maybe";
        if (p.getProduct(0).getAmount()+p.getProduct(1).getAmount()+p.getProduct(2).getAmount()+p.getProduct(3).getAmount()+p.getProduct(4).getAmount()==0) {
          System.out.println("You've sold your entire supply.");
          hasAnsContSell = true;
          wannaSell = false;
          System.out.println();
        }
        while (!hasAnsContSell) {
          System.out.println("Continue selling?");
          contSell = kb.next();
          hasAnsContSell = !catchYesNoError(contSell);
        }
        if (isNo(contSell))
          wannaSell=false;
      }
      
      // Upgrades phase
      getConnects(p);      
      if (p.getProduct(3).getAmount()>0) {
        boolean hasTakenAddy = false;
        while (!hasTakenAddy) {
          boolean hasAnsTakeAddy = false;
          String addies = "0";
          while (!hasAnsTakeAddy) {
            System.out.println("How many Addies would you like to take this week?");        
            addies = kb.next();
            hasAnsTakeAddy = isInt(addies);
          } 
          int numAddy = Integer.parseInt(addies);
          if (numAddy > 7 || numAddy > p.getProduct(3).getAmount())
            System.out.println("No way you can take that many.");
          else {
            p.addGPA((double) (numAddy/7*0.1));
            p.getProduct(3).addAmount(-numAddy);      
            hasTakenAddy = true;
          }
        }
      }        

      // Risk phase
      double caught = Math.random();
      if (caught < p.getRisk()) {
        gotCaught();
        break;
      }
      if (p.getGPA() < 2.0) {
        System.out.println("You had a long conversation with the Dean of Studies today.");
        System.out.println("It looks like your academic talents might be better suited to a different school.");
        System.out.println("Sorry fam, but your grades suck - you out.");
        break;
      }        
      
      System.out.println();
      p.addRisk(-.005);
      time.addWeek();
    }
    
    if (time.getWeek()>30) 
      System.out.println("CONGRATULATIONS! You have finished the year with $" + p.getMoney());
    kb.close();
  }
  
  private static String[] yeses = {"y","yes","yeah","ok","okay","sure"};
  private static boolean isYes(String y)
  {
    for (String yes : yeses)
      if (y.equalsIgnoreCase(yes))
        return true;
    return false;
  }
  
  private static String[] nos = {"n","no","nah","nope"};
  private static boolean isNo(String y)
  {
    for (String no : nos)
      if (y.equalsIgnoreCase(no))
        return true;
    return false;
  }
  
  private static boolean isInt(String s)
  {
    for (int i=0; i<10; i++)
      if (s.indexOf(i+"") >= 0)
        return true;    
    System.out.println("LOL, reread the question.");
    return false;    
  }
  
  private static boolean catchYesNoError(String yn)
  {
    if (!isYes(yn) && !isNo(yn)) {
      System.out.println("Please answer with a yes or no");
      return true;
    }
    return false;
  }
  
  private static void displayStats(Player p) 
  {
    System.out.print("Balance: $" + p.getMoney() + "\t");
    System.out.print("Rep: " + (int)(p.getRep()+0.5) + "/100\t");
    System.out.print("GPA: " + (double)((int)(p.getGPA()*10+0.5))/10 + "\t");
    System.out.println();   
  }
  
  private static void displayStocks(Player p)
  {
    for (int i=0; i<Player.STOCKS.length; i++)
      System.out.print(Player.STOCKS[i] + ": " + p.getProduct(i).getAmount() + "     ");     
    System.out.println(); 
  }
  
  private static void displayCosts(Player p) 
  {
    for (int d=0; d<Player.STOCKS.length; d++)
      System.out.print(Player.STOCKS[d] + ": $" + Buy.COSTS[d] + "     ");
    System.out.println();
  }
  
  private static int[][] thisWeeksDemand(Player p, Sota t, Random r) 
  {    
    int[][] demands = new int[4][5];
    for (int g = 0; g < 4; g++) {
      for (int d = 0; d < 5; d++) {
        int demand = (int)(r.nextGaussian()*(double)(p.getProduct(d).getDemand(t.getTerm(),g))/6+p.getProduct(d).getDemand(t.getTerm(),g)+0.5);
        while (demand < 0)
          demand = (int)(r.nextGaussian()*(double)(p.getProduct(d).getDemand(t.getTerm(),g))/6+p.getProduct(d).getDemand(t.getTerm(),g)+0.5);
        demands[g][d] = demand;
      }        
    }
    
    for (int d=0; d<Player.STOCKS.length; d++) {
      System.out.print("Demand for " + Player.STOCKS[d] + ":     ");
      for (int g=0; g<GRADES.length; g++)
        System.out.print(GRADES[g] + " " + demands[g][d] + "\t");      
      System.out.println();
    }
    return demands;
  }
  
  private static void gotCaught() 
  {
    System.out.println();
    int rand = (int) (Math.random()*4);
    switch (rand) {
      case 0: {
        System.out.println("PDM gotchu fam, sorry.");
        break;
      }
      case 1: {
        System.out.println("SURPRISE! TK PhD busted your ass.");
        break;
      }
      case 2: {
        System.out.println("Damn, JK Elliot ran into you at the liquor store while she was stocking up...");
        break;
      }
      case 3: {
        System.out.println("Mr. Holley was out buying cigarettes and sussed you out. Shiiit.");
        break;
      }
    }
    System.out.println();
    System.out.println("GAME OVER");
  }
  
  private static void getConnects(Player p)
  {
    if (p.getRep()>=30 && !p.canBuyOrNah(2)) {
      System.out.println("You've gained enough rep. You now have a connect in Lawrence to buy weed.");
      p.getWeed();
    } 
    
    if (p.getRep()>=45 && !p.canBuyOrNah(3)) {
      System.out.println("You've gained enough rep. You now can buy Addy.");
      p.getAddy();
    } 
    
    if (p.getRep()>=60 && !p.canBuyOrNah(4)) {
      System.out.println("You've gained enough rep. You now have a connect in NY to buy coke.");
      p.getCoke();
    }       
  }  
}