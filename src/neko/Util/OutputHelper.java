package neko.Util;

import neko.Interfaces.IHunter;
import neko.Profile;

/**
 * Created by Dominik on 30.07.2016.
 */
public class OutputHelper
{
    // Implements frequently used methods helped in outputting neko data

    /**
     * <b>Retrieves the Discord User ID from the saved Profile.</b>
     * @param hunter IHunter or implementation to retrieve UserID from.
     * @return Returns UserID as String.
     */
    public static String GetUserID(IHunter hunter)
    {
        return hunter.GetProfile().GetUserID();
    }

    /**
     * <b>Retrieves Room ID of IHunter object.</b>
     * @param hunter IHunter or implementation to retrieve RoomID from.
     * @return Returns RoomID as String.
     */
    public static String GetRoomID(IHunter hunter)
    {
        return hunter.GetRoom().GetRoomID();
    }

    /**
     * <b>Retrieve Passcode of IHunter object.</b>
     * @param hunter IHunter or implementation to retrieve Room Passcode from.
     * @return Returns Passcode as String.
     */
    public static String GetRoomPass(IHunter hunter)
    {
        return hunter.GetRoom().GetPasscode();
    }

    /**
     * <b>Retrieve Room Description of IHunter object.</b>
     * @param hunter IHunter or implementation to retrieve Room Description from.
     * @return Returns Description as String.
     */
    public static String GetDescription(IHunter hunter)
    {
        return hunter.GetRoom().GetDescription();
    }

    /**
     * <b>Retrieve Hunter Rank of IHunter object.</b>
     * @param hunter IHunter or implementation to retrieve Hunter Rank from.
     * @return Returns Hunter Rank as int.
     */
    public static int GetHunterRank(IHunter hunter)
    {
        return hunter.GetProfile().GetHunterRank().GetRank();
    }

    /**
     * <b>Overload:</b> Retrieve Hunter Rank of Profile object instead.
     * @param profile Profile object to retrieve Hunter Rank from.
     * @return Returns Hunter Rank as int.
     */
    public static int GetHunterRank(Profile profile)
    {
        return profile.GetHunterRank().GetRank();
    }
}
