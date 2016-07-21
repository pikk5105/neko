package neko;

/**
 * Created by Dominik on 21.07.2016.
 */

import java.util.ArrayList;
import java.util.List;

public class Room
{
    private String roomID   = "";
    private String passcode = "";
    private Hunt hunt       = null;

    protected int slots     = 4;
    private int usedSlots   = 1;
    private String roomDescription = "";
    private List<Profile> hunters   = new ArrayList<Profile>();

    public Room(String newRoomDescription,
                String newRoomID,
                String newPasscode,
                int newHuntRank,
                String newMonster,
                boolean newIsDeviant,
                short newDeviantLevel)
    {
        SetDescription(newRoomDescription);
        SetRoomID(newRoomID);
        SetPasscode(newPasscode);
        if(newIsDeviant)
        {
            hunt = new Hunt(newHuntRank, newMonster, Hunt.HuntType.Deviant, newDeviantLevel);
        }
        else
        {
            hunt = new Hunt(newHuntRank, newMonster);
        }
    }

    public Room(String newRoomDescription,
                String newRoomID,
                String newPasscode,
                int newHuntRank,
                String newMonster,
    {
        SetDescription(newRoomDescription);
        SetRoomID(newRoomID);
        SetPasscode(newPasscode);
        hunt = new Hunt(newHuntRank, newMonster);
    }

    public Hunt GetHunt()
    {
        return hunt;
    }

    public void SetHunt(int huntRank, String monster, int deviantLevel)
    {
        hunt.SetMonster(monster);
        hunt.SetDeviantLevel(deviantLevel);
    }

    public void SetHunt(int huntRank, String monster)
    {
        hunt.SetMonster(monster);
        hunt.SetDeviantLevel(0);
    }

    public void SetDescription(String description)
    {
        roomDescription = roomDescription;
    }

    public String GetDescription()
    {
        return roomDescription;
    }

    public void SetRoomID(String id)
    {
        roomID  =   id;
    }

    public String GetRoomID()
    {
        return roomID;
    }

    public void SetPasscode(String pass)
    {
        passcode = pass;
    }

    public String GetPasscode()
    {
        return passcode;
    }

    public void SetUsedSlots(int slots)
    {
        usedSlots = slots;
    }

    public void IncUsedSlots()
    {
        if(usedSlots < slots)
        {
            usedSlots++;
        }
    }

    public void DecUsedSlots()
    {
        if(usedSlots > 1)
        {
            usedSlots--;
        }
    }

    public int GetUsedSlots()
    {
        return usedSlots;
    }

    public List<Profile> GetHuntersList()
    {
        return hunters;
    }

    public void AddHunter(Profile profile)
    {
        hunters.add(hunters.size() + 1, profile);
    }

    public void RemoveHunter(Profile profile)
    {
        for (int i = hunters.size(); i > 0; i--)
        {
            if(hunters.get(i).GetUserID() != null && hunters.get(i).equals(profile))
            {
                hunters.remove(i);
            }
        }
    }
}
