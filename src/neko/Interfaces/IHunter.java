package neko.Interfaces;

import neko.Room;
import neko.Filters;
import neko.Profile;

/**
 * Created by Dominik on 30.07.2016.
 */
public interface IHunter
{
    default Profile GetProfile() {return null;}

    default void SetProfile(Profile profile){}

    default Room GetRoom(){return null;}

    default void SetRoom(Room r){}

    default Filters GetFilters(){return null;}

    default void SetFilters(){}
}
