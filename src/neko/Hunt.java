package neko;

public class  Hunt
{
  public enum HuntType
  {
    Low,
    High,
    Deviant
  }

  //HuntType huntType = new HuntType();

  private int huntRank        = 1;
  private String monster      = "";
  private short deviantLevel  = 1;
  private String difficulty   = "";

  public Hunt(int newHuntRank, String newMonster, HuntType newHuntDifficulty, short newDeviantLevel)
  {
    this.monster      = newMonster;
    this.difficulty   = newHuntDifficulty.toString();
    this.deviantLevel = newDeviantLevel;
  }

  public Hunt(int newHuntRank, String newMonster, HuntType newHuntDifficulty)
  {
    this.monster      = newMonster;
    this.difficulty   = newHuntDifficulty.toString();
  }

  public Hunt(int newHuntRank, HuntType newHuntDifficulty)
  {
    this.difficulty   = newHuntDifficulty.toString();
  }

  public void SetMonster(String newMonster)
  {
    monster = newMonster;
  }

  public String GetMonster()
  {
    return monster;
  }

  public void SetHuntType(HuntType newHuntType)
  {
    difficulty = newHuntType.toString();
  }

  public String GetHuntType()
  {
    return difficulty;
  }

  public void SetDeviantLevel(short newLevel)
  {
    deviantLevel = newLevel;
  }

  public void IncDeviantLevel()
  {
    SetDeviantLevel(deviantLevel++);
  }

  public short GetDeviantLevel()
  {
    return deviantLevel;
  }
}
