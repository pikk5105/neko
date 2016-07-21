/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;
//add ask command info when the neko thing happens!
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brady
 */
public class Leader
{
    Profile profile     = null;
    private Room room   = null;
    OffsetDateTime now  = OffsetDateTime.now();

    //search filters
    int rankH             = 999;
    int rankL             = 1;
    int slots             = 3;
    String weapon         = "";
    String role           = "";
    String lookingToHunt  = "";
    String huntTypeSearch = "";     // high low deviant

    List<Merc> mercList = new ArrayList<>();    //list from search
    Merc requestFrom = null;

    Leader(Profile newProfile,
           int newHuntRank,
           String newRoomDescription,
           String newHallID,
           String newPasscode,
           boolean newIsDeviant,
           short newDeviantLevel)
    {
        this.profile    = newProfile;
    }

    public Room GetRoom()
    {
        return room;
    }
}
