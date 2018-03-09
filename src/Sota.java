public class Sota 
{
  private int week = 1;
  public int getWeek()
  {
    return week;
  }
  
  private int term = 0;
  public String[] TERMS = {"Fall", "Fall Finals", "Winter", "Winter Finals", "Spring", "Spring Finals"};
  public String getTermName()
  {
    return TERMS[term];
  }
  public int getTerm()
  {
    return term;
  }
  
  public void addWeek()
  {
    week++;
    if (week >= 10 && (week % 10 == 0 || week % 10 == 1))
      term++;
  }
  
  public boolean isFinalsWeek()
  {
    return week != 0 && week % 10 == 0;
  }
}
