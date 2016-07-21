package neko;

/**
 * Created by Dominik on 20.07.2016.
 */

public class Hunt
{
    public enum HuntType
    {
        Low,
        High,
        Deviant
    }

    private int huntRank        = 1;
    private String monster      = "";
    private int deviantLevel  = 1;
    private String difficulty   = "";

    Hunt(int newHuntRank, String newMonster, HuntType newHuntDifficulty, short newDeviantLevel)
    {
        this.huntRank     = newHuntRank;
        this.monster      = newMonster;
        DecideHuntType(newHuntRank, true);
        this.deviantLevel = newDeviantLevel;
    }

    Hunt(int newHuntRank, String newMonster)
    {
        this.huntRank     = newHuntRank;
        this.monster      = newMonster;
        DecideHuntType(newHuntRank, false);
    }

    Hunt(int newHuntRank)
    {
        this.huntRank     = newHuntRank;
        DecideHuntType(newHuntRank, false);
    }

    public void SetMonster(String newMonster)
    {
        monster = newMonster;
    }

    public String GetMonster()
    {
        return monster;
    }

    private void DecideHuntType(int huntRank, boolean deviantHunt)
    {
        if(deviantHunt)
        {
            SetHuntType(HuntType.Deviant);
        }
        else
        {
            if (huntRank > 4)
            {
                SetHuntType(HuntType.High);
            }
            if (huntRank <= 3)
            {
                SetHuntType(HuntType.Low);
            }
        }
    }

    public void SetHuntType(HuntType newHuntType)
    {
        difficulty = newHuntType.toString();
    }

    public String GetHuntType()
    {
        return difficulty;
    }

    public void SetDeviantLevel(int newLevel)
    {
        deviantLevel = newLevel;
    }

    public void IncDeviantLevel()
    {
        SetDeviantLevel(deviantLevel++);
    }

    public int GetDeviantLevel()
    {
        return deviantLevel;
    }
}