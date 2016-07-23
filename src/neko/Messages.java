package neko;

/**
 * Created by Dominik on 21.07.2016.
 */
public class Messages
{
    private Messages instance;

    private Messages(){}

    public Messages GetInstance()
    {
        if(instance == null)
        {
            instance =  new Messages();
        }
        return instance;
    }

    //  TODO: "Nya **__"+event.getAuthorName()+"__**! check your meowssages to fill your guild card info to start having a Nekool time."

    //  TODO: "Nya **__"+event.getAuthorName()+"__**! send me a personal meowssage to list yourself, delist yourself, or ask me for help"

    //  TODO: "I am currently helping "+leaderList.size()+" leaders and "+mercList.size()+" hunters find a group!\nWhisper me to join in on the meownster hunting fun!"

    //  TODO: "You are not listed as a leader, so I do moewt have gathering hall informeowtion."

    /*  TODO: "here is the informeowtion for "+event.getAuthorName()+"'s hunt:\n"
     *                                           + "Hall ID: "+user.hallID+"\n"
     *   + "Password: "+user.passcode+"\n"
     *   + "Discription: "+user.hunting
     */

    //  TODO: "I couldnt find any hunters with that name!",null

    //  TODO: "User is no longer in the guild."

    /*  TODO: "Hunter name: **__"+event.getJDA().getUserById(found.UserID).getUsername()+"__** (Rank `"+found.rank
     *                      +"`)\nRoles: "+found.Roles
     *                      +"\nWeapons: "+found.Weapons
     */

    //  TODO: "hunter may have corrupted data, please let @pikk know so he may try to fix his dumb error"

    //  TODO: "I found "+users.size()+" hunters, be more specific please!"

    /*  TODO: "Nya **__"+event.getAuthorName()+"__**!\n"
     *              + "if you were looking to talk to me, simply call me with \"/neko\" and I will help you from there."
     *              + ""
     */

    //  TODO: "Nya **__"+event.getAuthorName()+"__**!\n Nice to see you again!" + ""

    //  TODO: "how can I help you?"

    //  TODO: "NYA! THAT'S ME!"

    //  TODO: "I couldnt find any hunters with that name!"

    //  TODO: "User is no longer in the guild."

    /*  TODO: "Hunter name: **__"+event.getJDA().getUserById(found.UserID).getUsername()+"__** (Rank `"+found.rank
     *                      +"`)\nRoles: "+found.Roles
     *                      +"\nWeapons: "+found.Weapons
     */

    //  TODO: "hunter may have corrupted data, please let @pikk know so he may try to fix his dumb error"

    //  TODO: "I found "+users.size()+" hunters, be more specific please!"

    //  TODO: "Nyokay! "+str+" has been added to your weapons!"

    //  TODO: "Nyokay! "+str+" has been added to your roles!"

    //  TODO: ("Sorry, I could Moewnt find a role or weapon with that name. do you want to see \"roles\" or \"weapons\"?"

    //  TODO: "Nyokay! "+str+" has been removed!"

    //  TODO: "I could meownt find that role or weapon of yours. do you want to see \"myRoles\" or \"myWeapons\"?"

    //  TODO: recognized Meownster Hunter Roles:

    //  TODO: "These are your current Roles:\n"

    //  TODO: "These are your current Weapons:\n"

    //  TODO: "Meownster Hunter Weapons:\n"

    //  TODO: s+="prowler is my favorite!"

    //  TODO: "you are Rank "+Integer.toString(p.rank)

    //  TODO: "look who thinks he is a wise cat!"

    //  TODO: "NYATTAA! you are rank "+Integer.toString(p.rank)

    //  TODO: "look who thinks he is a wise cat!"

    //  TODO: "I changed your rank to "+Integer.toString(p.rank)+""

    //  TODO: "MEOWCH, You are bad at math! Try using a number next time."

    //  TODO: "Aye Sir!"

    //  TODO: "you are already enlisted as a Leader, use `delist` to unlist yourself before enlisting again"

    //  TODO: "you are already enlisted as a Mercenary, use `delist` to unlist yourself before enlisting again"

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and your current rank(`"+p.rank+"`)."

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and 10."

    //  TODO: "I have listed you as a leader! search for hunters!"

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and your current rank(`"+p.rank+"`)."

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and your current rank(`"+p.rank+"`)."

    //  TODO: "I have listed you as a leader! search for hunters!"

    //  TODO: "you are already enlisted as a Leader, use `delist` to unlist yourself before enlisting again"

    //  TODO: "you are already enlisted as a Mercenary, use `delist` to unlist yourself before enlisting again"

    //  TODO: "you can only join hunts from rank `1` to `"+p.rank+"`, sorry \uD83D\uDE3F"

    //  TODO: "I have listed you as a Mercenary! search for hunter groups!"

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and 10 for deviant hunts."

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and your current rank(`"+p.rank+"`)."

    //  TODO: "I have listed you as a Mercenary! search for hunter groups!"

    //  TODO: "MEOWCH, You are bad at math! Try using a number between 1 and your current rank(`"+p.rank+"`)."

    //  TODO: "I have removed you from active leaders, I hope you had a meowvelous hunt."

    //  TODO: "I have removed you from active Mercenaries, I hope you had a meowvelous hunt."

    //  TODO: "You were not enlisted in the first place, baka."

    //  TODO: "there are "+Integer.toString(leaderList.size())+" leaders and " + Integer.toString(mercList.size())+" mercenaries",null);

    //  TODO: "These are the filters Leaders can use\n"

    //  TODO: "These are the filters Mercenaries can use\n"

    /*  TODO: Integer.toString(number)+". **__"+event.getJDA().getUserById(mercen.profile.UserID).getUsername()
     *                                   +"__** (Hunter rank: `"+Integer.toString(mercen.profile.rank)
     *                                  +"`)\n     Hunt rank: `"+Integer.toString(mercen.huntRank)
     *                                  +"`  Description: "+mercen.hunting+"\n"
     */

    /*  TODO: mercs
     *                  + "`ask <X>` - asks a Hunter by number on your most recent search to join your room, or invite you to their room\n"
     *                          + "\ndid you find what you were looking for? here are some command remeownders :D\n"
     *                          + "`filters` - lists all filters that you can currently use to find Hunters.\n"
     *                          + "`addFilter <filter> <X>` - filters hunters with X\n"
     *                          + "`removeFilter <filter>` - clears that filter field\n"
     */

    //  TODO: "`ask #` will ask that person on the list to join"

    //  TODO: "use a number 1-"+Integer.toString(leader.mercList.size())+"."

    //  TODO: "he already has a request pending from a leader, I cant ask him untill he answers that request."

    //  TODO: "Invitation has been sent to "+event.getJDA().getUserById(m.profile.UserID).getUsername()

    //  TODO: "MEOWCH, You are bad at math! Try using a number next time."

    //  TODO: "you can only have 0-3 slots for hunters not including yourself **__"+event.getJDA().getUserById(p.UserID).getUsername()+"__**"

    //  TODO: "Slots in group changed to `"+leader.slots+"`"

    //  TODO: "MEOWCH, You are bad at math! Try using a number next time."

    //  TODO: "you have no requests waiting."

    /*  TODO: "I sent **__"+event.getJDA().getUserById(leader.requestFrom.profile.UserID).getUsername()+"__** the hall info\n"
     * + "you have "+leader.slots+" slots left.
     */

    /*  TODO: "Join **__"+event.getJDA().getUserById(leader.profile.UserID).getUsername()+"__**'s hunt!\n"
     *          + "HallID:   "+leader.hallID+"\n"
     *          + "Passcode: "+leader.passcode
     */

    //  TODO: "I have delisted you, happy hunting!"

    //  TODO: "you declined **__"+event.getJDA().getUserById(leader.requestFrom.profile.UserID).getUsername()+"__**'s request"

    //  TODO: "**__"+event.getJDA().getUserById(leader.profile.UserID).getUsername()+"__** declined your request"

    //  TODO: "not enough informeowshin!"

    //  TODO: "I only have hunters from 1-999. sorry \uD83D\uDE3F"

    //  TODO: "Search will now find hunters with a rank of "+Integer.toString(rank)+" or higher."

    //  TODO: "MEOWCH, You are bad at math! Try using a number next time."

    //  TODO: "search will now find hunters with a rank of "+Integer.toString(rank)+" or lower."

    //  TODO: ("search will now find hunters with the role "+str+"."

    //  TODO: "I could not find that role."

    //  TODO: ("search will now find hunters who use a "+str+"."

    //  TODO: "I could not find that weapon."

    //  TODO: ("search will now try to find hunters with that interest."

    //  TODO: "to use this catmand, type `removefilter <filtername>`."

    //  TODO: "filter "+parsedm[1]+" removed."

    /*  TODO: Integer.toString(number)+".    **__"+event.getJDA().getUserById(lead.profile.UserID).getUsername()
     *                                  +"__**  (Hunter rank:  `"+Integer.toString(lead.profile.rank)
     *                                  +"`)\n      hunting rank: `"+Integer.toString(lead.huntRank)
     *                                  +"`,  Description:  "+lead.hunting+"\n"
     */

    //  TODO: "I could Meownt find any groups like that"

    /*  TODO: leaders
     *                  + "`ask <X>` - asks a Hunter by number on your most recent search to join your room, or invite you to their room\n"
     *                  + "\ndid you find what you were looking for? here are some catmand remeownders\n"
     *                  + "`filters` - lists all filters that you can currently use to find Hunters.\n"
     *                  + "`addFilter <filter> <X>` - filters hunters with X\n"
     *                  + "`removeFilter <filter>` - clears that filter field\n"
     */

    //  TODO: ("`ask #` will ask that person on the list to join"

    //  TODO: "use a number 1-"+Integer.toString(merc.leaderList.size())+"."

    //  TODO: "that hunter already has a request pending from a leader, I cant ask untill the current request is answered."

    //  TODO: "**__"+event.getJDA().getUserById(merc.profile.UserID).getUsername()+"__** would like to hunt with you, would you like to accept? (Y/N)"

    //  TODO: "Invitation requested from "+event.getJDA().getUserById(l.profile.UserID).getUsername()

    //  TODO: "you have no requests waiting."

    //  TODO: "I sent **__"+event.getJDA().getUserById(merc.profile.UserID).getUsername()+"__** the hall info\n"

    //  TODO: "you have "+merc.requestFrom.slots+" slots left"

    /*  TODO: "Join **__"+event.getJDA().getUserById(merc.requestFrom.profile.UserID).getUsername()+"__**'s hunt!\n"
     *                      + "HallID:   "+merc.requestFrom.hallID+"\n"
     *                      + "Passcode: "+merc.requestFrom.passcode
     */

    //  TODO: "I have delisted you, happy hunting!"

    //  TODO: "you have no requests waiting."

}
