/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.login.LoginException;

import neko.Util.JDAHelper;
import neko.Util.SpectraHelper;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.impl.GuildImpl;
import net.dv8tion.jda.events.message.GenericMessageEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 *
 * @author Brady
 */

//CATastrophy
//
public class Neko extends ListenerAdapter{

    // Secret info = new Secret();
    private final List<String> modIDs = new ArrayList<>();
    // info.getModIDs();
    private String token = "";

    private void SetToken()
    {
        token = "";
    }

    private void SetModIDs()
    {
        modIDs.add("");
    }

    String[] Roles = {"Damage", "Tank", "Status", "Support", "Mounter", "Other"};
    String[] Weapons = {"GS", "LS", "SnS", "DB", "Hammer", "HH", "Lance", "GL", "SA", "CB", "IG", "LBG", "HBG", "Bow", "Prowler"};
    String[] huntTypes = {"Low", "High", "Deviant"};

    String[] leaderFilters ={"Rank>", "Rank<", "Role", "Weapon", "HuntRank", "HuntType", "LookingToHunt"};
    String[] mercFilters = {"Rank>", "Rank<", "HuntRank", "HuntType", "Hunting"};

    List<Leader> leaderList = new ArrayList<>();
    List<Merc> mercList = new ArrayList<>();
    
    String atMe = ".*<@!?196047400811495424>.*";

    MessageHandler msg = MessageHandler.GetInstance() ;

    //private profiles[] profilesList; not needed
    // msg      msg.errors.InvalidHunterRank()
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String message = event.getMessage().getRawContent();

        if (message.equalsIgnoreCase("/neko") || message.equalsIgnoreCase("%neko")){
            Profile profile = Profiles.getInstance().getProfile(event.getAuthor().getId());
            if (profile==null){
                //make profile
                Profiles.getInstance().set(new String[] {event.getAuthor().getId(),"","","1"});

                event.getChannel().sendMessageAsync(msg.general.NewUserGreeting(event.getAuthor().getUsername()), null);
                event.getAuthor().getPrivateChannel().sendMessageAsync(msg.info.helpMessage1,null);
                event.getAuthor().getPrivateChannel().sendMessageAsync(msg.info.helpMessage2,null);
                event.getJDA().getUserById("135251434445733888").getPrivateChannel().sendMessageAsync(event.getAuthor().getId()+":0",null);
            }
            else{

                event.getChannel().sendMessageAsync(msg.general.UsageReminder(event.getAuthor().getUsername()),null);
                event.getAuthor().getPrivateChannel().sendMessageAsync("how can I help you?",null);
            }
        }

        else if(message.equalsIgnoreCase("/neko hunters"))
        {
            purgeLists(event.getJDA());
            event.getChannel().sendMessageAsync(msg.info.ListHuntersInQueueGreeting(leaderList.size(), mercList.size()),null);
        }

        else if(message.toLowerCase().equals("/neko myhunt"))
        {
            Leader user=null;
            for(Leader u :leaderList){
                if (u.GetProfile().GetUserID().equals(event.getAuthor().getId())){
                    user=u;
                }
            }

            if(user==null){
                event.getChannel().sendMessageAsync(msg.error.NotListedAsLeader(),null);
                return;
            }

            event.getChannel().sendMessageAsync(msg.info.GetRoomInfo(token, user),null);

            return;
        }

        else if(message.toLowerCase().startsWith("/neko lookup ")){
            String name = message.substring(13);
            List<User> users = findUsers(name, event.getJDA().getGuildById("120889695658967041"));
            if (users.isEmpty()){
                event.getChannel().sendMessageAsync(msg.error.NoSearchResult(),null);
                return;
            }
            try{
                Profile found = Profiles.getInstance().getProfile(users.get(0).getId());
                if(event.getJDA().getUserById(found.GetUserID())==null){
                    event.getChannel().sendMessageAsync(msg.error.UserLeftServer(),null);
                }
                if (users.size()==1){
                    event.getChannel().sendMessageAsync(msg.info.GetProfileInfo(event.getJDA().getUserById(found.GetUserID()).getUsername(), found),null);
                    return;

                }
            } catch(Exception e){
                //event.getChannel().sendMessageAsync(msg.error.CorruptedProfile(),null);
                System.out.println("error with: " +users.get(0).getId());
                //System.out.println(event.getJDA().getUserById(Profiles.getInstance().getProfile(users.get(0).getId()).UserID).getUsername());
            }
            event.getChannel().sendMessageAsync(msg.error.MultipleUsersFound(users.size()),null);
            return;
        }

        //let /neko anything bring a help menu up in that chat
        else if (message.toLowerCase().startsWith("/neko") || message.toLowerCase().startsWith("%neko")){
            Profile profile = Profiles.getInstance().getProfile(event.getAuthor().getId());
            if (profile==null){
                event.getChannel().sendMessageAsync(msg.general.UsageReminder(event.getAuthorName()),null);
            }else{
                event.getChannel().sendMessageAsync("Nya **__"+event.getAuthorName()+"__**!\n Nice to see you again!", null);
                event.getAuthor().getPrivateChannel().sendMessageAsync("how can I help you?",null);
            }
        }
        else if(message.matches(atMe)){
            event.getChannel().sendMessageAsync("NYA! THAT'S ME!", null);
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
    {

        boolean isBot = event.getAuthor().isBot();
        if(isBot)
        {
            return;
        }

        Profile p=Profiles.getInstance().getProfile(JDAHelper.GetAuthorID(event));
        if (p==null)
        {
            //make profile
            Profiles.getInstance().set(new String[] {JDAHelper.GetAuthorID(event),"","","1"});

            //event.getChannel().sendMessageAsync("Nya **__"+event.getAuthorName()+"__**! check your meowssages to fill your guild card info to start having a Nekool time.", null);
            JDAHelper.SendDM(event, msg.info.helpMessage1);
            JDAHelper.SendDM(event, msg.info.helpMessage2);
            return;
        }

        String message = JDAHelper.GetMessageRaw(event);
        if (message.equalsIgnoreCase("help")){
            JDAHelper.Respond(event, msg.info.helpMessage1);
            JDAHelper.Respond(event, msg.info.helpMessage2);
        }

        else if(message.toLowerCase().startsWith("lookup "))
        {
            String name = message.substring(7);
            List<User> users = findUsers(name, JDAHelper.GetMHGH(event));
            if (users.isEmpty())
            {
                JDAHelper.Respond(event, msg.error.NameNotFound());
                return;
            }
            try
            {
                Profile found = Profiles.getInstance().getProfile(users.get(0).getId());
                if(event.getJDA().getUserById(found.GetUserID())==null)
                {
                    JDAHelper.Respond(event, msg.error.UserLeftServer());
                }

                if (users.size()==1)
                {
                    JDAHelper.Respond(event, msg.info.GetProfileInfo(JDAHelper.GetUsername(event, found.GetUserID()), found));
                    return;
                }

            }
            catch(Exception e)
            {
                //event.getChannel().sendMessageAsync("hunter may have corrupted data, please let @pikk know so he may try to fix his dumb error",null);
                System.out.println("error with: " +users.get(0).getId());
                //System.out.println(event.getJDA().getUserById(Profiles.getInstance().getProfile(users.get(0).getId()).UserID).getUsername());
            }
            JDAHelper.Respond(event, msg.error.MultipleUsersFound(users.size()));
            return;

        }

        else if(message.toLowerCase().startsWith("add "))
        {
            //add a thing
            for(String str : Weapons)
            {
                if (message.toLowerCase().endsWith(str.toLowerCase()))
                {
                    p.addWeapon(str);
                    Profiles.getInstance().setProfile(p);
                    JDAHelper.Respond(event, msg.success.WeaponAdded(str));
                    return;
                }
            }

            for(String str : Roles)
            {
                if (message.toLowerCase().endsWith(str.toLowerCase()))
                {
                    p.addRole(str);
                    Profiles.getInstance().setProfile(p);
                    JDAHelper.Respond(event, msg.success.RoleAdded(str));
                    return;
                }
            }
            JDAHelper.Respond(event, msg.error.InvalidRoleOrWeapon());

        }

        else if(message.toLowerCase().startsWith("remove "))
        {
            //remove a thing
            for(String str : p.GetWeaponList())
            {
                if (message.toLowerCase().endsWith(str.toLowerCase()))
                {
                    p.removeWeapon(str);
                    Profiles.getInstance().setProfile(p);
                    JDAHelper.Respond(event, msg.success.WeaponRemoved(str));
                    return;
                }
            }

            for(String str : p.GetRoleList())
            {
                if (message.toLowerCase().endsWith(str.toLowerCase()))
                {
                    p.removeRole(str);
                    Profiles.getInstance().setProfile(p);
                    JDAHelper.Respond(event, msg.success.RoleRemoved(str));
                    return;
                }
            }
            JDAHelper.Respond(event, msg.error.RoleOrWeaponNotFound());
        }

        else if(message.equalsIgnoreCase("roles"))
        {
            //list roles
            JDAHelper.Respond(event, msg.info.RecognizedRoles(Roles));
        }

        else if(message.equalsIgnoreCase("myRoles"))
        {
            //list users roles
            JDAHelper.Respond(event, msg.info.UserRoles(p));
        }

        else if(message.equalsIgnoreCase("myWeapons"))
        {
            //list weapons
            JDAHelper.Respond(event,msg.info.UserWeapons(p));
        }

        else if(message.equalsIgnoreCase("weapons"))
        {
            JDAHelper.Respond(event, msg.info.RecognizedWeapons(Weapons));
        }

        else if(message.equalsIgnoreCase("myRank"))
        {
            //display users rank
            JDAHelper.Respond(event, msg.info.UserRank(p));
        }

        else if(message.equalsIgnoreCase("rankUp"))
        {
            if (p.GetHunterRank().GetRank()==999)
            {
                JDAHelper.Respond(event, msg.response.WiseCat());
                return;
            }
            p.GetHunterRank().RankUp();
            JDAHelper.Respond(event, msg.success.HunterRankUp(p));
            Profiles.getInstance().setProfile(p);

            //send message to spectra for rank up
            SpectraHelper.RankUpdateEvent(event, p);
            //add one to rank

        }

        else if(message.toLowerCase().startsWith("changerank "))
        {
            //changes rank to X
            String newInt=message.substring(11);
            try
            {
                int newRank=Integer.parseInt(newInt);
                if (newRank<0 || newRank>999)
                {
                    JDAHelper.Respond(event, msg.response.WiseCat());
                    return;
                }
                p.GetHunterRank().SetRank(newRank);
                JDAHelper.Respond(event, msg.success.HunterRankChanged(p));
                Profiles.getInstance().setProfile(p);
                //send message to spectra for rank up
                SpectraHelper.RankUpdateEvent(event, p);
            }
            catch (Exception e)
            {
                JDAHelper.Respond(event, msg.error.NotANumber());
            }
        }

        else if(message.equalsIgnoreCase("/shutdown")&&modIDs.contains(JDAHelper.GetAuthorID(event)))
        {
            JDAHelper.Message(event, "Aye Sir!");
            JDAHelper.Shutdown(event);
            Profiles.getInstance().shutdown();

        }

        else if(message.toLowerCase().startsWith("enlist leader "))
        {
            if(getLeaderObj(JDAHelper.GetAuthorID(event))!=null){
                JDAHelper.Respond(event,msg.error.AlreadyEnlisted(true));
                return;
            }
            if(getMercObj(event.getAuthor().getId())!=null){
                JDAHelper.Respond(event, msg.error.AlreadyEnlisted(true));
                return;
            }
            String[] strings = message.split(" ");
            int thisHuntRank=0;
            String s="";
            for(int i=5; i<strings.length; i++)
            {
                s+=strings[i]+" ";
            }
            try
            {
                thisHuntRank=Integer.parseInt(strings[4]);
                if (thisHuntRank<1 || thisHuntRank>p.GetHunterRank().GetRank())
                {
                    JDAHelper.Respond(event, msg.error.InvalidHuntRank(p, false));
                    return;
                }
            }
            catch(Exception e)
            {
                try
                {
                    if(strings[4].toLowerCase().charAt(0)=='d')
                    {//deviant hunt search
                        thisHuntRank=Integer.parseInt(strings[4].substring(1));//number past the d
                        if (thisHuntRank<1 || thisHuntRank>10)
                        {
                            event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, true),null);
                        }
                        Leader you = new Leader(p, thisHuntRank, s, strings[2], strings[3], true);
                        leaderList.add(you);
                        event.getChannel().sendMessageAsync(msg.success.Enlisted(true),null);
                    }
                    else
                    {
                        event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, true),null);
                    }
                    return;
                }
                catch(Exception ee)
                {
                    event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, false),null);
                    return;
                }
            }
            Leader you = new Leader(p, thisHuntRank, s, strings[2], strings[3], false);
            leaderList.add(you);
            JDAHelper.Respond(event, msg.success.Enlisted(true));
        }

        else if(message.toLowerCase().startsWith("enlist merc "))
        {
            if(getLeaderObj(JDAHelper.GetAuthorID(event))!=null)
            {
                JDAHelper.Respond(event, msg.error.AlreadyEnlisted(true));
                return;
            }
            if(getMercObj(event.getAuthor().getId())!=null)
            {
                JDAHelper.Respond(event, msg.error.AlreadyEnlisted(false));
                return;
            }
            String[] strings = message.split(" ");

            String s="";
            for(int i=3; i<strings.length; i++)
            {
                s+=strings[i]+" ";
            }
            int rank;
            try {
                rank=Integer.parseInt(strings[2]);
                if (rank<1 || rank>p.GetHunterRank().GetRank())
                {
                    JDAHelper.Respond(event, msg.error.InsufficientHunterRank(p));
                    return;
                }
                Merc you = new Merc(p, rank, s, false);
                mercList.add(you);
                JDAHelper.Respond(event, msg.success.Enlisted(false));
            } catch (Exception e)
            {
                try
                {
                    if(strings[2].toLowerCase().charAt(0)=='d') //deviant hunt search
                    {
                        rank=Integer.parseInt(strings[2].substring(1));//number past the d
                        if (rank<1 || rank>10)
                        {
                            JDAHelper.Respond(event, msg.error.InvalidHuntRank(p, true));
                        }
                        Merc you = new Merc(p, rank, s, true);
                        mercList.add(you);
                    }
                    else
                    {
                        JDAHelper.Respond(event, msg.error.InvalidHuntRank(p, false));
                    }
                    JDAHelper.Respond(event, msg.success.Enlisted(false));
                    return;
                }
                catch(Exception ee)
                {
                    JDAHelper.Respond(event, msg.error.InvalidHuntRank(p, false));
                    return;
                }
            }

        }

        else if(message.equalsIgnoreCase("delist"))
        {

            Leader lead = getLeaderObj(event.getAuthor().getId());
            if (lead != null)
            {
                leaderList.remove(lead);
                JDAHelper.Respond(event, msg.success.Delisted(true));
                return;
            }

            Merc merc = getMercObj(event.getAuthor().getId());
            if (merc != null)
            {
                mercList.remove(merc);
                JDAHelper.Respond(event, msg.success.Delisted(false));
                return;
            }
            JDAHelper.Respond(event, msg.error.AlreadyDelisted());
        }

        else if(message.equalsIgnoreCase("hunters"))
        {
            purgeLists(event.getJDA());
            JDAHelper.Respond(event, msg.info.ListHuntersInQueue(leaderList.size(), mercList.size()));
        }

        else   //need to be a leader or mercenary to get this far, or its an error which is handled at the end
        {
            boolean isLeader=false;
            Leader leader=null;
            Merc merc=null;
            boolean isMerc=false;
            for(Leader lead : leaderList)
            {
                if(p.GetUserID().equals(lead.GetProfile().GetUserID()))
                {
                    isLeader=true;
                    leader=lead;
                    leader.now=OffsetDateTime.now();
                    if(message.equalsIgnoreCase("filters"))
                    {
                        JDAHelper.Respond(event, msg.info.FiltersAvailable(leaderFilters, isLeader));
                    }
                    break;
                }
            }

            //

            if(!isLeader)
            {
                for(Merc mercen : mercList)
                {
                    if(p.GetUserID().equals(mercen.GetProfile().GetUserID()))
                    {
                        isMerc=true;
                        merc=mercen;
                        merc.now=OffsetDateTime.now();
                        if(message.equalsIgnoreCase("filters"))
                        {
                            JDAHelper.Respond(event, msg.info.FiltersAvailable(mercFilters, isLeader));
                            return;
                        }
                        break;
                    }
                }
            }
            else /*is a leader*/
            {
                if(message.equalsIgnoreCase("search")){
                    //todo: search for mercs
                    String mercs="";
                    leader.mercList.clear();
                    purgeLists(event.getJDA());
                    int number = 1;
                    for(Merc mercen: mercList)
                    {
                        if (mercForLeader(mercen, leader)){
                            leader.mercList.add(mercen);
                            mercs+=msg.info.MercListing(number, event.getJDA().getUserById(mercen.GetProfile().GetUserID()).getUsername(), mercen);
                            number++;
                        }
                    }
                    
                    if (mercs.equals(""))
                    {
                        JDAHelper.Respond(event, msg.error.NoSearchResult());
                        return;
                    }
                    String sendme = mercs + msg.response.RoomListAppendix();
                    ArrayList<String> messages = splitMessage(sendme);

                    for(String s:messages){
                        JDAHelper.Respond(event, s);
                    }
                    return;
                }

                else if(message.toLowerCase().startsWith("ask "))
                {
                    String[] parsedm = message.split(" ");
                    if (parsedm.length!= 2)
                    {
                        JDAHelper.Respond(event, msg.info.AskToJoin());
                        return;
                    }
                    try
                    {
                        int listIndex = Integer.parseInt(parsedm[1]);
                        if (listIndex>leader.mercList.size() || listIndex<=0) //not a viable index
                        {
                            JDAHelper.Respond(event, msg.info.UseNumberRange(leader.mercList.size()));
                            return;
                        }
                        listIndex--;
                        Merc m = leader.mercList.get(listIndex);
                        if (m.requestFrom != null)
                        {
                            JDAHelper.Respond(event, msg.error.HasRequestPending());
                            return;
                        }
                        m.requestFrom=leader;
                        JDAHelper.SendDMByID(event, m.GetProfile().GetUserID(), msg.response.AskHunter(JDAHelper.GetUsername(event, leader.GetProfile().GetUserID()), leader));
                        JDAHelper.Respond(event, msg.success.HunterAsked(JDAHelper.GetUsername(event, m.GetProfile().GetUserID())));
                    }
                    catch(Exception e)
                    {
                        JDAHelper.Respond(event, msg.error.NotANumber());
                    }
                    return;

                }

                else if(message.toLowerCase().startsWith("slots "))
                {
                    try
                    {
                        int s = Integer.parseInt(message.split(" ")[1]);
                        if (s<0||s>3)
                        {
                            JDAHelper.Respond(event, msg.error.InvalidSlotRange(JDAHelper.GetUsername(event, p.GetUserID())));
                            return;
                        }
                        leader.slots=s;
                        JDAHelper.Respond(event, msg.success.SlotsChanged(leader.slots));
                    }
                    catch(Exception e)
                    {
                        JDAHelper.Respond(event, msg.error.NotANumber());
                    }
                    return;
                }

                else if(message.equalsIgnoreCase("y"))
                {
                    if (leader.requestFrom == null)
                    {
                        JDAHelper.Respond(event, msg.info.NoRequests());
                        return;
                    }
                    leader.slots--;
                    event.getChannel().sendMessageAsync("I sent **__"+event.getJDA().getUserById(leader.requestFrom.GetProfile().GetUserID()).getUsername()+"__** the hall info\n"
                            + "you have "+leader.slots+" slots left.",null);
                    event.getJDA().getUserById(leader.requestFrom.GetProfile().GetUserID()).getPrivateChannel().sendMessageAsync("Join **__"+event.getJDA().getUserById(leader.GetProfile().GetUserID()).getUsername()+"__**'s hunt!\n"
                            + "HallID:   "+leader.GetRoom().GetRoomID()+"\n"
                            + "Passcode: "+leader.GetRoom().GetPasscode(),null);
                    event.getJDA().getUserById(leader.requestFrom.GetProfile().GetUserID()).getPrivateChannel().sendMessageAsync("I have delisted you, happy hunting!",null);
                    mercList.remove(leader.requestFrom);

                    leader.requestFrom=null;
                    return;
                }

                else if(message.equalsIgnoreCase("n")){
                    if (leader.requestFrom == null){
                        event.getChannel().sendMessageAsync("you have no requests waiting.",null);
                        return;
                    }
                    event.getChannel().sendMessageAsync("you declined **__"+event.getJDA().getUserById(leader.requestFrom.GetProfile().GetUserID()).getUsername()+"__**'s request",null);
                    event.getJDA().getUserById(leader.requestFrom.GetProfile().GetUserID()).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(leader.GetProfile().GetUserID()).getUsername()+"__** declined your request",null);
                    leader.requestFrom=null;
                    return;
                }

                else if(message.toLowerCase().startsWith("addfilter ")){
                    String[] parsedm = message.split(" ");
                    if (parsedm.length<3){
                        event.getChannel().sendMessageAsync("not enough informeowshin!",null);
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("rank>")){
                        try {
                            int rank=Integer.parseInt(parsedm[2]);
                            if (rank<1 || rank>999){
                                event.getChannel().sendMessageAsync("I only have hunters from 1-999. sorry \uD83D\uDE3F",null);
                                return;
                            }
                            leader.rankL=rank;
                            event.getChannel().sendMessageAsync("Search will now find hunters with a rank of "+Integer.toString(rank)+" or higher.",null);
                        } catch (Exception e){
                            event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                        }
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("rank<")){
                        try {
                            int rank=Integer.parseInt(parsedm[2]);
                            if (rank<1 || rank>999){
                                event.getChannel().sendMessageAsync("I only have hunts from 1-999. sorry \uD83D\uDE3F",null);
                                return;
                            }
                            leader.rankH=rank;
                            event.getChannel().sendMessageAsync("search will now find hunters with a rank of "+Integer.toString(rank)+" or lower.",null);
                        } catch (Exception e){
                            event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                        }
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("role")){
                        for(String str : Roles){
                            if(parsedm[2].equalsIgnoreCase(str)){
                                leader.role=str;
                                event.getChannel().sendMessageAsync("search will now find hunters with the role "+str+".",null);
                                return;
                            }
                        }
                        event.getChannel().sendMessageAsync("I could not find that role.",null);
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("weapon")){
                        for(String str : Weapons){
                            if(parsedm[2].equalsIgnoreCase(str)){
                                leader.weapon=str;
                                event.getChannel().sendMessageAsync("search will now find hunters who use a "+str+".",null);
                                return;
                            }
                        }
                        event.getChannel().sendMessageAsync("I could not find that weapon.",null);
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("lookingtohunt")){
                        String s="";
                        for(int i=2; i<parsedm.length; i++){
                            s+=parsedm[i];
                        }
                        leader.lookingToHunt = s;
                        event.getChannel().sendMessageAsync("search will now try to find hunters with that interest.",null);
                        return;
                    }

                }

                else if(message.toLowerCase().startsWith("removefilter ")){
                    String[] parsedm = message.split(" ");
                    if (parsedm.length!=2){
                        event.getChannel().sendMessageAsync("to use this catmand, type `removefilter <filtername>`.",null);
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("rank>")){
                        leader.rankL = leader.GetRoom().GetHunt().GetHuntRank();
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("rank<")){
                        leader.rankH = 999;
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("role")){
                        leader.role="";
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("weapon")){
                        leader.weapon="";
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("lookingtohunt")){
                        leader.lookingToHunt="";
                        return;
                    }
                    event.getChannel().sendMessageAsync("filter "+parsedm[1]+" removed.",null);
                }
            }

            if(isMerc){
                if(message.equalsIgnoreCase("search")){
                    //search for leaders
                    String leaders="";
                    merc.leaderList.clear();
                    int number=1;
                    purgeLists(event.getJDA());
                    for(Leader lead: leaderList){
                        if (leaderForMerc(lead, merc)){


                                //can be added(will use more search parameters later)
                                merc.leaderList.add(lead);
                                leaders+=Integer.toString(number)+".    **__"+event.getJDA().getUserById(lead.GetProfile().GetUserID()).getUsername()
                                        +"__**  (IHunter rank:  `"+Integer.toString(lead.GetProfile().GetHunterRank().GetRank())
                                        +"`)\n      hunting rank: `"+Integer.toString(lead.GetRoom().GetHunt().GetHuntRank())
                                        +"`,  Description:  "+lead.GetRoom().GetDescription()+"\n";
                                number++;
                            
                        }
                    }

                    if(leaders.equals("")){
                        event.getChannel().sendMessageAsync("I could Meownt find any groups like that",null);
                        return;
                    }
                    String sendme = leaders
                            + "`ask <X>` - asks a IHunter by number on your most recent search to join your room, or invite you to their room\n"
                            + "\ndid you find what you were looking for? here are some catmand remeownders\n"
                            + "`filters` - lists all filters that you can currently use to find Hunters.\n"
                            + "`addFilter <filter> <X>` - filters hunters with X\n"
                            + "`removeFilter <filter>` - clears that filter field\n";
                    ArrayList<String> messageTwo = splitMessage(sendme);
                    for(String s:messageTwo){
                        event.getChannel().sendMessageAsync(s,null);
                    }
                    return;
                }

                else if(message.toLowerCase().startsWith("ask ")){
                    String[] parsedm = message.split(" ");
                    if (parsedm.length!= 2){
                        event.getChannel().sendMessageAsync("`ask #` will ask that person on the list to join",null);
                        return;
                    }

                    try{
                        int listIndex = Integer.parseInt(parsedm[1]);
                        if (listIndex>merc.leaderList.size() || listIndex<=0){//not a viable index
                            event.getChannel().sendMessageAsync("use a number 1-"+Integer.toString(merc.leaderList.size())+".",null);
                            return;
                        }
                        listIndex--;
                        Leader l = merc.leaderList.get(listIndex);
                        if (l.requestFrom != null || l.slots<=0){
                            event.getChannel().sendMessageAsync("that hunter already has a request pending from a leader, I cant ask untill the current request is answered.",null);
                            return;
                        }
                        l.requestFrom=merc;
                        event.getJDA().getUserById(l.GetProfile().GetUserID()).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(merc.GetProfile().GetUserID()).getUsername()+"__** would like to hunt with you, would you like to accept? (Y/N)",null);
                        event.getChannel().sendMessageAsync("Invitation requested from "+event.getJDA().getUserById(l.GetProfile().GetUserID()).getUsername(),null);
                    }catch(Exception e){
                        event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                    }
                    return;

                }

                else if(message.equalsIgnoreCase("y")){
                    if (merc.requestFrom == null){
                        event.getChannel().sendMessageAsync("you have no requests waiting.",null);
                        return;
                    }
                    merc.requestFrom.slots--;
                    event.getJDA().getUserById(merc.requestFrom.GetProfile().GetUserID()).getPrivateChannel().sendMessageAsync("I sent **__"+event.getJDA().getUserById(merc.GetProfile().GetUserID()).getUsername()+"__** the hall info\n"
                            + "you have "+merc.requestFrom.slots+" slots left",null);
                    event.getChannel().sendMessageAsync("Join **__"+event.getJDA().getUserById(merc.requestFrom.GetProfile().GetUserID()).getUsername()+"__**'s hunt!\n"
                            + "HallID:   "+merc.requestFrom.GetRoom().GetRoomID()+"\n"
                            + "Passcode: "+merc.requestFrom.GetRoom().GetPasscode(),null);
                    mercList.remove(merc);
                    event.getChannel().sendMessageAsync("I have delisted you, happy hunting!",null);
                    return;
                }

                else if(message.equalsIgnoreCase("n")){
                    if (merc.requestFrom == null){
                        event.getChannel().sendMessageAsync("you have no requests waiting.",null);
                        return;
                    }
                    event.getChannel().sendMessageAsync("you declined **__"+event.getJDA().getUserById(merc.requestFrom.GetProfile().GetUserID()).getUsername()+"__**'s request",null);
                    event.getJDA().getUserById(merc.requestFrom.GetProfile().GetUserID()).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(merc.GetProfile().GetUserID()).getUsername()+"__**declined your request",null);
                    merc.requestFrom=null;
                    return;
                }

                else if (message.toLowerCase().startsWith("addfilter ")){
                    String[] parsedm = message.split(" ");
                    if (parsedm.length<3){
                        event.getChannel().sendMessageAsync("not enough informeowshin!",null);
                        return;
                    }

                    else if(parsedm[1].equalsIgnoreCase("rank>")){
                        try {
                            int rank=Integer.parseInt(parsedm[2]);
                            if (rank<1 || rank>999){
                                event.getChannel().sendMessageAsync("I only have hunters from 1-999. sorry \uD83D\uDE3F",null);
                                return;
                            }
                            merc.rankL=rank;
                            event.getChannel().sendMessageAsync("search will now find leaders with a rank of "+Integer.toString(rank)+" or higher.",null);
                        } catch (Exception e){
                            event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                        }
                        return;
                    }

                    else if(parsedm[1].equalsIgnoreCase("rank<")){
                        try {
                            int rank=Integer.parseInt(parsedm[2]);
                            if (rank<1 || rank>999){
                                event.getChannel().sendMessageAsync("I only have hunts from 1-999. sorry \uD83D\uDE3F",null);
                                return;
                            }
                            merc.rankH=rank;
                            event.getChannel().sendMessageAsync("search will now find leaders with a rank of "+Integer.toString(rank)+" or lower.",null);
                        } catch (Exception e){
                            event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                        }
                        return;
                    }

                    else if(parsedm[1].equalsIgnoreCase("HuntRank")){
                        try {
                            int rank=Integer.parseInt(parsedm[2]);
                            if (rank<1 || rank>999){
                                event.getChannel().sendMessageAsync("I only have hunts from 1-999. sorry \uD83D\uDE3F",null);
                                return;
                            }
                            merc.huntRankSearch=rank;
                            event.getChannel().sendMessageAsync("search will now find leaders with a rank of "+Integer.toString(rank)+" or lower.",null);
                        } catch (Exception e){
                            event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                        }
                        return;
                    }

                    else if(parsedm[1].equalsIgnoreCase("HuntType")){
                        for(String type: huntTypes){
                            if(type.equalsIgnoreCase(parsedm[2])){
                                merc.huntTypeSearch=type;
                                event.getChannel().sendMessageAsync("search will now find "+type+" rank leaders.",null);
                                return;
                            }
                            event.getChannel().sendMessageAsync("I do not recogmeowize that hunt type.",null);
                        }
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("hunting")){
                        String s="";
                        for(int i=2; i<parsedm.length; i++){
                            s+=parsedm[i];
                        }
                        merc.lookingToHunt = s;
                        event.getChannel().sendMessageAsync("search will now try to find leaders with that interest.",null);
                        return;
                    }
                }

                else if(message.toLowerCase().startsWith("removefilter ")){
                    String[] parsedm = message.split(" ");
                    if (parsedm.length!=2){
                        event.getChannel().sendMessageAsync("to use this catmand, type `removefilter <filtername>`.",null);
                        return;
                    }

                    else if(parsedm[1].toLowerCase().equals("rank>")){
                        merc.rankL = 1;
                    }

                    else if(parsedm[1].toLowerCase().equals("rank<")){
                        merc.rankH = 999;
                    }

                    else if(parsedm[1].toLowerCase().equals("huntrank")){
                        merc.huntRankSearch=0;
                    }

                    else if(parsedm[1].toLowerCase().equals("hunttype")){
                        merc.huntTypeSearch="";
                    }

                    else if(parsedm[1].toLowerCase().equals("hunting")){
                        merc.lookingToHunt="";
                    }

                    event.getChannel().sendMessageAsync("filter "+parsedm[1]+" removed.",null);
                    return;
                }
            }


            event.getChannel().sendMessageAsync(msg.error.WrongInput(),null);

        }
    }


    public void profileLookup(String name, MessageReceivedEvent event){
        List<User> users = findUsers(name, event.getJDA().getGuildById("120889695658967041"));
            if (users.isEmpty()){
                event.getChannel().sendMessageAsync(msg.error.NoSearchResult(),null);
                return;
            }
            try{
                Profile found = Profiles.getInstance().getProfile(users.get(0).getId());
                if(event.getJDA().getUserById(found.GetUserID())==null){
                    event.getChannel().sendMessageAsync(msg.error.UserLeftServer(),null);
                }
                if (users.size()==1){
                    event.getChannel().sendMessageAsync(msg.info.GetProfileInfo(event.getJDA().getUserById(found.GetUserID()).getUsername(), found),null);
                    return;

                }
            } catch(Exception e){
                //event.getChannel().sendMessageAsync(msg.error.CorruptedProfile(),null);
                System.out.println("error with: " +users.get(0).getId());
                //System.out.println(event.getJDA().getUserById(Profiles.getInstance().getProfile(users.get(0).getId()).UserID).getUsername());
            }
            event.getChannel().sendMessageAsync(msg.error.MultipleUsersFound(users.size()),null);
    }
    
    Leader getLeaderObj(String userID){
        for(Leader lead: leaderList){
            if(lead.GetProfile().GetUserID().equals(userID)){
                return lead;
            }
        }
        return null;
    }

    Merc getMercObj(String userID){
        for(Merc merc: mercList){
            if(merc.GetProfile().GetUserID().equals(userID)){
                return merc;
            }
        }
        return null;
    }
    public static List<User> findUsers(String query, Guild guild)
    {
        String id;
        String discrim = null;
        if(query.matches("<@!?\\d+>"))
        {
            id = query.replaceAll("<@!?(\\d+)>", "$1");
            User u = guild.getJDA().getUserById(id);
            if(((GuildImpl)guild).getUserRoles().get(u)!=null)
                return Collections.singletonList(u);
        }
        else if(query.matches("^.*#\\d{4}$"))
        {
            discrim = query.substring(query.length()-4);
            query = query.substring(0,query.length()-5).trim();
        }
        ArrayList<User> exact = new ArrayList<>();
        ArrayList<User> wrongcase = new ArrayList<>();
        ArrayList<User> startswith = new ArrayList<>();
        ArrayList<User> contains = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for(User u: guild.getUsers())
        {
            String nickname = guild.getNicknameForUser(u);
            if(discrim!=null && !u.getDiscriminator().equals(discrim))
                continue;
            if(u.getUsername().equals(query) || (nickname!=null && nickname.equals(query)))
                exact.add(u);
            else if (exact.isEmpty() && (u.getUsername().equalsIgnoreCase(query) || (nickname!=null && nickname.equalsIgnoreCase(query))))
                wrongcase.add(u);
            else if (wrongcase.isEmpty() && (u.getUsername().toLowerCase().startsWith(lowerQuery) || (nickname!=null && nickname.toLowerCase().startsWith(lowerQuery))))
                startswith.add(u);
            else if (startswith.isEmpty() && (u.getUsername().toLowerCase().contains(lowerQuery) || (nickname!=null && nickname.toLowerCase().contains(lowerQuery))))
                contains.add(u);
        }
        if(!exact.isEmpty())
            return exact;
        if(!wrongcase.isEmpty())
            return wrongcase;
        if(!startswith.isEmpty())
            return startswith;
        return contains;
    }

    private static ArrayList<String> splitMessage(String stringtoSend)
    {
        ArrayList<String> msgs =  new ArrayList<>();
        if(stringtoSend!=null)
        {
            stringtoSend = stringtoSend.replace("@everyone", "@\u200Beveryone").replace("@here", "@\u200Bhere").trim();
            while(stringtoSend.length()>2000)
            {
                int leeway = 2000 - (stringtoSend.length()%2000);
                int index = stringtoSend.lastIndexOf("\n", 2000);
                if(index<leeway)
                    index = stringtoSend.lastIndexOf(" ", 2000);
                if(index<leeway)
                    index=2000;
                String temp = stringtoSend.substring(0,index).trim();
                if(!temp.equals(""))
                    msgs.add(temp);
                stringtoSend = stringtoSend.substring(index).trim();
            }
            if(!stringtoSend.equals(""))
                msgs.add(stringtoSend);
        }
        return msgs;
    }

    //make sure all people who have been afk for 4 hours get removed
    public void purgeLists(JDA myJDA){
        List<Leader> deleteList1 = new ArrayList<>();
        List<Merc> deleteList2 = new ArrayList<>();

        for(Leader lead: leaderList){
            if(OffsetDateTime.now().isAfter(lead.now.plusHours(4)) || (myJDA.getUserById(lead.GetProfile().GetUserID()).getOnlineStatus() == OnlineStatus.OFFLINE) ){
                deleteList1.add(lead);
            }
        }
        leaderList.removeAll(deleteList1);

        for(Merc mercen: mercList){
            if(OffsetDateTime.now().isAfter(mercen.now.plusHours(4)) || (myJDA.getUserById(mercen.GetProfile().GetUserID()).getOnlineStatus() == OnlineStatus.OFFLINE)){
                deleteList2.add(mercen);
            }
        }
        mercList.removeAll(deleteList2);
    }
    
    public static boolean mercForLeader(Merc mercen, Leader leader){
        if (((mercen.GetProfile().GetHunterRank().GetRank()<=leader.rankH && mercen.GetProfile().GetHunterRank().GetRank()>=leader.rankL) ||(mercen.GetHuntRank()<=leader.rankH && mercen.GetHuntRank()>=leader.rankL))
                && (mercen.hunting.toLowerCase().replaceAll("\\s+","").contains(leader.lookingToHunt.toLowerCase().replaceAll("\\s+","")) || leader.lookingToHunt.toLowerCase().replaceAll("\\s+","").contains(mercen.hunting.toLowerCase().replaceAll("\\s+","")))
                && (mercen.hasRole(leader.role) || leader.role.equals(""))
                && (mercen.hasWeapon(leader.weapon) || leader.weapon.equals(""))
                && (leader.huntTypeSearch.equals("") || leader.huntTypeSearch.equals(mercen.huntType))
                && mercen.requestFrom==null)
        {
            return true;
        }
        return false;
    }
    
    public static boolean leaderForMerc(Leader lead, Merc merc)
    {
        if (lead.slots>0
                && (merc.huntTypeSearch.equals("")|| merc.huntTypeSearch.equals(lead.GetRoom().GetHunt().GetHuntType()))
                && ((lead.GetProfile().GetHunterRank().GetRank()<=merc.rankH && lead.GetProfile().GetHunterRank().GetRank()>=merc.rankL) || (lead.GetRoom().GetHunt().GetHuntRank()<=merc.rankH && lead.GetRoom().GetHunt().GetHuntRank()>=merc.rankL))
                && (lead.GetRoom().GetHunt().GetMonster().toLowerCase().replaceAll("\\s+","").contains(merc.lookingToHunt.toLowerCase().replaceAll("\\s+","")) || (merc.lookingToHunt.toLowerCase().replaceAll("\\s+","").contains(lead.GetRoom().GetHunt().GetMonster().toLowerCase().replaceAll("\\s+",""))))
                && (lead.requestFrom==null)){
            return true;
        }

        
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Neko().init();
        // TODO code application logic here
    }

    public void init() {
        SetToken();
        SetModIDs();
        Profiles.getInstance().read();
        try {
            new JDABuilder().addListener(this).setBotToken(token).setAudioEnabled(false).buildAsync();
        } catch (LoginException | IllegalArgumentException ex) {
            System.err.println("ERROR - Building JDA : "+ex.toString());
            System.exit(1);
        }
    }

}