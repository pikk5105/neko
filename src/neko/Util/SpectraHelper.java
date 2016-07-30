package neko.Util;

import neko.Profile;
import net.dv8tion.jda.events.message.priv.PrivateMessageReceivedEvent;

/**
 * Created by Dominik on 30.07.2016.
 */
public class SpectraHelper
{
    private static final String _spectraID = "135251434445733888";

    /**
     * <b>Sends Direct Message to Spectra</b>
     *
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param message Message content.
     */
    public static void SendDM(PrivateMessageReceivedEvent event, String message)
    {
        JDAHelper.SendDMByID(event, _spectraID,  message);
    }

    /**
     * Notifies Spectra about Hunter Rank changes.
     *
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param profile Profile that contains the Hunter Rank to send.
     */
    public static void RankUpdateEvent(PrivateMessageReceivedEvent event, Profile profile)
    {
        String msg = event.getAuthor().getId()+":"+Integer.toString(profile.GetHunterRank().GetRank());
        SendDM(event, msg);
    }
}
