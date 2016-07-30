package neko.Util;

import neko.Interfaces.IHunter;
import neko.Profile;

/**
 * Created by Dominik on 30.07.2016.
 */
public class OutputHelper
{
    // Implements frequently used methods helped in outputting neko data

    public static String GetUserID(IHunter hunter)
    {
        return hunter.GetProfile().GetUserID();
    }

    public static String GetRoomID(IHunter hunter)
    {
        return hunter.GetRoom().GetRoomID();
    }

    public static String GetRoomPass(IHunter hunter)
    {
        return hunter.GetRoom().GetPasscode();
    }

    public static String GetDescription(IHunter hunter)
    {
        return hunter.GetRoom().GetDescription();
    }

    public static int GetHunterRank(IHunter hunter)
    {
        return hunter.GetProfile().GetHunterRank().GetRank();
    }

    public static int GetHunterRank(Profile profile)
    {
        return profile.GetHunterRank().GetRank();
    }
}
