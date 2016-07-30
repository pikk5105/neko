/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;
//add ask command info when the neko thing happens!
import neko.Interfaces.IHunter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brady
 */

public class Leader implements IHunter
{
    private Profile profile     = null;
    private Room room   = null;
    private Filters filters = null;
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

    Leader(Profile profile,
           int huntRank,
           String roomDescription,
           String roomID,
           String roomPasscode,
           boolean isDeviantHunt)
    {
        this.profile    = profile;
    }

    Leader(Profile profile, Room room, Filters filters)
    {
        this.profile = profile;
        this.room = room;
        this.filters = filters;
    }

    Leader(Profile profile)
    {
        this.profile = profile;
    }

    @Override
    public void SetRoom(Room r) {

    }

    @Override
    public Room GetRoom()
    {
        return room;
    }

    @Override
    public void SetProfile(Profile profile)
    {
        this.profile = profile;
    }

    @Override
    public Profile GetProfile()
    {
        return profile;
    }


    @Override
    public void SetFilters()
    {

    }

    @Override
    public Filters GetFilters()
    {
        return null;
    }

}
