package neko.Util;

import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.priv.PrivateMessageReceivedEvent;

/**
 * Created by Dominik on 30.07.2016.
 */
public class JDAHelper
{
    // Implements helper methods to interact with frequently used JDA methods in a more efficient manner

    /**
     * <b>Sends Direct Message to a user.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param message Message to send user.
     */
    public static void SendDM(PrivateMessageReceivedEvent event, String message)
    {
        event.getAuthor().getPrivateChannel().sendMessageAsync(message, null);
    }

    /**
     * <b>Sends Direct Message to a user identified by UserID.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param UserID User Identifier whom will be messaged.
     * @param message Message to send user.
     */
    public static void SendDMByID(PrivateMessageReceivedEvent event, String UserID, String message)
    {
        event.getJDA().getUserById(UserID).getPrivateChannel().sendMessageAsync(message, null);
    }

    /**
     * <b>Responds to a user on direct-messaging.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param message Message to send user.
     */
    public static void Respond(PrivateMessageReceivedEvent event, String message)
    {
        event.getChannel().sendMessageAsync(message, null);
    }

    /**
     * <b>Messages user directly within context.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param message Message to send.
     */
    public static void Message(PrivateMessageReceivedEvent event, String message)
    {
        event.getChannel().sendMessage(message);
    }

    /**
     * <b>Retrieves Guild Object by ID.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param guildID GuildID to look for.
     * @return Returns found Guild object.
     */
    public static Guild GetGuildByID(PrivateMessageReceivedEvent event, String guildID)
    {
        return event.getJDA().getGuildById(guildID);
    }

    /**
     * <b>Retrieves Monster Hunter Gathering Hall Object</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @return Returns MHGH guild object.
     */
    public static Guild GetMHGH(PrivateMessageReceivedEvent event)
    {
        String _mhghID = "120889695658967041";
        return GetGuildByID(event, _mhghID);
    }

    /**
     * <b>Gets UserID of messaging user.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @return Returns Discord UserID.
     */
    public static String GetAuthorID(PrivateMessageReceivedEvent event)
    {
        return event.getAuthor().getId();
    }

    /**
     * <b>Retrieves User object by UserID</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param id Discord UserID to retrieve User from.
     * @return Returns User object.
     */
    public static User GetUserByID(PrivateMessageReceivedEvent event, String id)
    {
        return event.getJDA().getUserById(id);
    }

    /**
     * <b>Retrieves Discord Username</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @param userID Discord UserID to retrieve username for.
     * @return Returns found Username.
     */
    public static String GetUsername(PrivateMessageReceivedEvent event, String userID)
    {
        return event.getJDA().getUserById(userID).getUsername();
    }

    /**
     * <b>Retrieves Private Message content.</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     * @return Returns Message Content.
     */
    public static String GetMessageRaw(PrivateMessageReceivedEvent event)
    {
        return event.getMessage().getRawContent();
    }

    /**
     * <b>Declares Shutdown of JDA instance. Careful, user filtering needs to be done in advance (ADMIN Privileges)</b>
     * @param event Required JDA PrivateMessageReceivedEvent.
     */
    public static void Shutdown(PrivateMessageReceivedEvent event)
    {
        event.getJDA().shutdown();
    }
}
