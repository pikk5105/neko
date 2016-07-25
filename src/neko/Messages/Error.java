package neko.Messages;

import neko.Profile;

/**
 * Created by Dominik on 25.07.2016.
 */
public class Error
{

    public String NotListedAsLeader()
    {
        return "You are not listed as a leader, so I do moewt have gathering hall informeowtion.";
    }

    public String NameNotFound()
    {
        return "I couldnt find any hunters with that name!";
    }

    public String UserLeftServer()
    {
        return "User is no longer in the guild.";
    }

    public String CorruptedProfile()
    {
        return "hunter may have corrupted data, please let @pikk know so he may try to fix his dumb error";
    }

    public String MultipleUsersFound(int number)
    {
        return "I found "+number+" hunters, be more specific please!";
    }

    public String InvalidRoleOrWeapon()
    {
        return "Sorry, I could Moewnt find a role or weapon with that name. Do you want to see \"roles\" or \"weapons\"?";
    }

    public String NotANumber()
    {
        return "MEOWCH, You are bad at math! Try using a number next time.";
    }

    public String InvalidHuntRank(Profile profile, boolean isDeviantHunt)
    {
        if(isDeviantHunt)
        {
            return "MEOWCH, You are bad at math! Try using a number between 1 and 10.";
        }
        else
        {
            return "MEOWCH, You are bad at math! Try using a number between 1 and your current rank(`"+profile.GetHunterRank().GetRank()+"`).";
        }
    }

    public String AlreadyEnlisted(boolean asLeader)
    {
        if(asLeader)
        {
            return "you are already enlisted as a Leader, use `delist` to unlist yourself before enlisting again";
        }
        else
        {
            return "you are already enlisted as a Mercenary, use `delist` to unlist yourself before enlisting again";
        }
    }

    public String InsufficientHunterRank(Profile profile)
    {
        return "you can only join hunts from rank `1` to `"+profile.GetHunterRank().GetRank()+"`, sorry \uD83D\uDE3F";
    }

    public String AlreadyDelisted()
    {
        return "You were not enlisted in the first place, baka.";
    }

    public String NoSearchResult()
    {
        return "I could meownt find any hunters like that.";
    }
}
