package neko.Messages;

import neko.Profile;

/**
 * Created by Dominik on 25.07.2016.
 */
public class Success
{
    public String WeaponAdded(String weapon)
    {
        return "Nyokay! "+weapon+" has been added to your weapons!";
    }

    public String RoleAdded(String role)
    {
        return "Nyokay! "+role+" has been added to your roles!";
    }

    public String WeaponRemoved(String weapon)
    {
        return "Nyokay! "+weapon+" has been removed!";
    }

    public String RoleRemoved(String role)
    {
        return "Nyokay! "+role+" has been removed!";
    }

    public String HunterRankUp(Profile profile)
    {
        return "NYATTAA! You are rank "+Integer.toString(profile.GetHunterRank().GetRank())+" now!";
    }

    public String HunterRankChanged(Profile profile)
    {
        return "I changed your rank to "+Integer.toString(profile.GetHunterRank().GetRank())+"";
    }

    public String Enlisted(boolean asLeader)
    {
        if(asLeader)
        {
            return "I have listed you as a leader! Search for hunters!";
        }
        else
        {
            return "I have listed you as a Mercenary! Search for hunter groups!";
        }
    }

    public String Delisted(boolean asLeader)
    {
        if(asLeader)
        {
            return "I have removed you from active leaders, I hope you had a meowvelous hunt.";
        }
        else
        {
            return "I have removed you from active Mercenaries, I hope you had a meowvelous hunt.";
        }
    }

    public String HunterAsked(String username)
    {
        return "Invitation has been sent to "+username;
    }

    public String SlotsChanged(int slots)
    {
        return "Slots in group changed to `"+slots+"`";
    }
}
