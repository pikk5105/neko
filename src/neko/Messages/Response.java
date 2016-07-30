package neko.Messages;

import neko.Leader;

/**
 * Created by Dominik on 25.07.2016.
 */
public class Response
{
    public String WiseCat()
    {
        return "look who thinks he is a wise cat!";
    }

    public String ShutdownAcknowledged()
    {
        return "Aye Sir!";
    }

    public String RoomListAppendix()
    {
        return  "`ask <X>` - Asks a Hunter by number on your most recent search to join your room, or invite you to their room\n"
                + "\nDid you find what you were looking for? here are some command remeownders :D\n"
                + "`filters` - Lists all filters that you can currently use to find Hunters.\n"
                + "`addFilter <filter> <X>` - Filters hunters with X\n"
                + "`removeFilter <filter>` - Clears that filter field\n";
    }

    public String AskHunter(String username, Leader leader)
    {
        return "**__"+username+"__** would like you to hunt\n\""+leader.GetRoom().GetHunt().GetMonster()+"\"\nwould you like to accept? (Y/N)";
    }
}
