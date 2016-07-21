package neko;

public class HunterRank
{
  private int rank = 0;

  HunterRank(int newRank)
  {
    SetRank(newRank);
  }

  public void SetRank(int newRank)
  {
    rank = newRank;
  }

  public int GetRank()
  {
    return rank;
  }

  public String GetRankType()
  {
    if(rank <= 4)
    {
      return "Low";
    }
    else
    {
      return "High";
    }
  }
}
