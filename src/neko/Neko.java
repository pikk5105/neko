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
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.OnlineStatus;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.impl.GuildImpl;
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

Secret info = new Secret();
private final List<String> modIDs = info.getModIDs();
//info.getModIDs();
private final String token = info.getToken();


String[] Roles = {"Damage", "Tank", "Status", "Support", "Mounter", "Other"};
String[] Weapons = {"GS", "LS", "SnS", "DB", "Hammer", "HH", "Lance", "GL", "SA", "CB", "IG", "LBG", "HBG", "Bow", "Prowler"};
String[] huntTypes = {"Low", "High", "Deviant"};

String[] leaderFilters ={"Rank>", "Rank<", "Role", "Weapon", "HuntRank", "HuntType", "LookingToHunt"};
String[] mercFilters = {"Rank>", "Rank<", "HuntRank", "HuntType", "Hunting"};

List<Leader> leaderList = new ArrayList<>();
List<Merc> mercList = new ArrayList<>();

MessageHandler msg = MessageHandler.GetInstance() ;

//private profiles[] profilesList; not needed
// msg      msg.errors.InvalidHunterRank()
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
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

        else if(message.equalsIgnoreCase("/neko hunters")){
            purgeLists(event.getJDA());
            event.getChannel().sendMessageAsync(msg.info.ListHuntersInQueueGreeting(leaderList.size(), mercList.size()),null);
        }
        
        else if(message.toLowerCase().equals("/neko myhunt")){
            Leader user=null;
            for(Leader u :leaderList){
                if (u.profile.GetUserID().equals(event.getAuthor().getId())){
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
        else if(message.matches(".*<@!?196047400811495424>.*")){
            event.getChannel().sendMessageAsync("NYA! THAT'S ME!", null);
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if(event.getAuthor().isBot()){
            return;
        }
        Profile p=Profiles.getInstance().getProfile(event.getAuthor().getId());
        if (p==null){
                //make profile
                Profiles.getInstance().set(new String[] {event.getAuthor().getId(),"","","1"});
                
                //event.getChannel().sendMessageAsync("Nya **__"+event.getAuthorName()+"__**! check your meowssages to fill your guild card info to start having a Nekool time.", null);
                event.getAuthor().getPrivateChannel().sendMessageAsync(msg.info.helpMessage1,null);
                event.getAuthor().getPrivateChannel().sendMessageAsync(msg.info.helpMessage2,null);
                return;
        }
        
        String message = event.getMessage().getRawContent();
        if (message.equalsIgnoreCase("help")){
            event.getChannel().sendMessageAsync(msg.info.helpMessage1, null);
            event.getChannel().sendMessageAsync(msg.info.helpMessage2, null);
        }
        
        else if(message.toLowerCase().startsWith("lookup ")){
            String name = message.substring(7);
            List<User> users = findUsers(name, event.getJDA().getGuildById("120889695658967041"));
            if (users.isEmpty()){
                event.getChannel().sendMessageAsync(msg.error.NameNotFound(),null);
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
                //event.getChannel().sendMessageAsync("hunter may have corrupted data, please let @pikk know so he may try to fix his dumb error",null);
                System.out.println("error with: " +users.get(0).getId());
                //System.out.println(event.getJDA().getUserById(Profiles.getInstance().getProfile(users.get(0).getId()).UserID).getUsername());
            }
            event.getChannel().sendMessageAsync(msg.error.MultipleUsersFound(users.size()),null);
            return;
            
        }
        
        else if(message.toLowerCase().startsWith("add ")){
            //add a thing
            for(String str : Weapons){
                if (message.toLowerCase().endsWith(str.toLowerCase())){
                    p.addWeapon(str);
                    Profiles.getInstance().setProfile(p);
                    event.getChannel().sendMessageAsync(msg.success.WeaponAdded(str),null);
                    return;
                }
            }
            
            for(String str : Roles){
                if (message.toLowerCase().endsWith(str.toLowerCase())){
                    p.addRole(str);
                    Profiles.getInstance().setProfile(p);
                    event.getChannel().sendMessageAsync(msg.success.RoleAdded(str),null);
                    return;
                }
            }
            
            event.getChannel().sendMessageAsync(msg.error.InvalidRoleOrWeapon(),null);
            
        }
        
        else if(message.toLowerCase().startsWith("remove ")){
            //remove a thing
            for(String str : p.GetWeaponList()){
                if (message.toLowerCase().endsWith(str.toLowerCase())){
                    p.removeWeapon(str);
                    Profiles.getInstance().setProfile(p);
                    event.getChannel().sendMessageAsync(msg.success.WeaponRemoved(str),null);
                    return;
                }
            }
            
            for(String str : p.GetRoleList()){
                if (message.toLowerCase().endsWith(str.toLowerCase())){
                    p.removeRole(str);
                    Profiles.getInstance().setProfile(p);
                    event.getChannel().sendMessageAsync(msg.success.RoleRemoved(str),null);
                    return;
                }
            }
            
            event.getChannel().sendMessageAsync("I could meownt find that role or weapon of yours. do you want to see \"myRoles\" or \"myWeapons\"?",null);
            
        }
        
        else if(message.equalsIgnoreCase("roles")){
            //list roles
            event.getChannel().sendMessageAsync(msg.info.RecognizedRoles(Roles),null);
        }
        
        else if(message.equalsIgnoreCase("myRoles")){
            //list users roles
            event.getChannel().sendMessageAsync(msg.info.UserRoles(p),null);
        }
        
        else if(message.equalsIgnoreCase("myWeapons")){
            //list weapons
            event.getChannel().sendMessageAsync(msg.info.UserWeapons(p),null);
        }
        
        else if(message.equalsIgnoreCase("weapons")){
            event.getChannel().sendMessageAsync(msg.info.RecognizedWeapons(Weapons),null);
        }
        
        else if(message.equalsIgnoreCase("myRank")){
            //display users rank
            event.getChannel().sendMessageAsync(msg.info.UserRank(p),null);
        }
        
        else if(message.equalsIgnoreCase("rankUp")){
            if (p.GetHunterRank().GetRank()==999){
                event.getChannel().sendMessageAsync(msg.response.WiseCat(),null);
                return;
            }
            p.GetHunterRank().RankUp();
            event.getChannel().sendMessageAsync(msg.success.HunterRankUp(p),null);
            Profiles.getInstance().setProfile(p);
            //send message to spectra for rank up
            event.getJDA().getUserById("135251434445733888").getPrivateChannel().sendMessageAsync(event.getAuthor().getId()+":"+Integer.toString(p.GetHunterRank().GetRank()),null);
            //add one to rank
            
        }
        
        else if(message.toLowerCase().startsWith("changerank ")){
            //changes rank to X
            String newInt=message.substring(11);
            try {
                int newRank=Integer.parseInt(newInt);
                if (newRank<0 || newRank>999){
                    event.getChannel().sendMessageAsync(msg.response.WiseCat(),null);
                    return;
                }
                p.GetHunterRank().SetRank(newRank);
                event.getChannel().sendMessageAsync(msg.success.HunterRankChanged(p),null);
                Profiles.getInstance().setProfile(p);
                //send message to spectra for rank up
                event.getJDA().getUserById("135251434445733888").getPrivateChannel().sendMessageAsync(event.getAuthor().getId()+":"+Integer.toString(p.GetHunterRank().GetRank()),null);
            } catch (Exception e){
                event.getChannel().sendMessageAsync(msg.error.NotANumber(),null);
            }
        }
        
        else if(message.equalsIgnoreCase("/shutdown")&&modIDs.contains(event.getAuthor().getId())){
            event.getChannel().sendMessage("Aye Sir!");
            event.getJDA().shutdown();
            Profiles.getInstance().shutdown();
            
        }
        
        else if(message.toLowerCase().startsWith("enlist leader ")){
            if(getLeaderObj(event.getAuthor().getId())!=null){
                event.getChannel().sendMessageAsync(msg.error.AlreadyEnlisted(true),null);
                return;
            }
            if(getMercObj(event.getAuthor().getId())!=null){
                event.getChannel().sendMessageAsync(msg.error.AlreadyEnlisted(true),null);
                return;
            }
            String[] strings = message.split(" ");
            int thisHuntRank=0;
            String s="";
            for(int i=5; i<strings.length; i++){
                s+=strings[i]+" ";
            }
            try{
                thisHuntRank=Integer.parseInt(strings[4]);
                if (thisHuntRank<1 || thisHuntRank>p.GetHunterRank().GetRank()){
                    event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, false),null);
                    return;
                }
            }catch(Exception e){
                try{
                    if(strings[4].toLowerCase().charAt(0)=='d'){//deviant hunt search
                        thisHuntRank=Integer.parseInt(strings[4].substring(1));//number past the d
                        if (thisHuntRank<1 || thisHuntRank>10){
                            event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, true),null);
                        }
                        Leader you = new Leader(p, thisHuntRank, s, strings[2], strings[3], true);
                        leaderList.add(you);
                        event.getChannel().sendMessageAsync(msg.success.Enlisted(true),null);
                    }else{
                        event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, true),null);
                    }
                    return;
                }catch(Exception ee){
                    event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, false),null);
                    return;
                }
            }
            Leader you = new Leader(p, thisHuntRank, s, strings[2], strings[3], false);
            leaderList.add(you);
            event.getChannel().sendMessageAsync("I have listed you as a leader! search for hunters!",null);
            
        }
        
        else if(message.toLowerCase().startsWith("enlist merc ")){
            if(getLeaderObj(event.getAuthor().getId())!=null){
                event.getChannel().sendMessageAsync(msg.error.AlreadyEnlisted(true),null);
                return;
            }
            if(getMercObj(event.getAuthor().getId())!=null){
                event.getChannel().sendMessageAsync(msg.error.AlreadyEnlisted(false),null);
                return;
            }
            String[] strings = message.split(" ");
            
            String s="";
            for(int i=3; i<strings.length; i++){
                s+=strings[i]+" ";
            }
            int rank;
            try {
                rank=Integer.parseInt(strings[2]);
                if (rank<1 || rank>p.GetHunterRank().GetRank()){
                    event.getChannel().sendMessageAsync(msg.error.InsufficientHunterRank(p),null);
                    return;
                }
                Merc you = new Merc(p, rank, s, false);
                mercList.add(you);
                event.getChannel().sendMessageAsync(msg.success.Enlisted(false),null);
            } catch (Exception e){
                try{
                    if(strings[2].toLowerCase().charAt(0)=='d'){//deviant hunt search
                        rank=Integer.parseInt(strings[2].substring(1));//number past the d
                        if (rank<1 || rank>10){
                            event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, true),null);
                        }
                        Merc you = new Merc(p, rank, s, true);
                        mercList.add(you);
                    }else{
                        event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, false),null);
                    }
                    event.getChannel().sendMessageAsync(msg.success.Enlisted(false),null);
                    return;
                }catch(Exception ee){
                    event.getChannel().sendMessageAsync(msg.error.InvalidHuntRank(p, false),null);
                    return;
                }
            }
            
        }
        
        else if(message.equalsIgnoreCase("delist")){
            
            Leader lead = getLeaderObj(event.getAuthor().getId());
            if (lead != null){
                leaderList.remove(lead);
                event.getChannel().sendMessageAsync(msg.success.Delisted(true),null);
                return;
            }
            
            Merc merc = getMercObj(event.getAuthor().getId());
            if (merc != null){
                mercList.remove(merc);
                event.getChannel().sendMessageAsync(msg.success.Delisted(false),null);
                return;
            }
            event.getChannel().sendMessageAsync(msg.error.AlreadyDelisted(),null);
        }
        
        else if(message.equalsIgnoreCase("hunters")){
            purgeLists(event.getJDA());
            event.getChannel().sendMessageAsync(msg.info.ListHuntersInQueue(leaderList.size(), mercList.size()),null);
        }
        
        else{//need to be a leader or mercenary to get this far, or its an error which is handled at the end
            
            boolean isLeader=false;
            Leader leader=null;
            Merc merc=null;
            boolean isMerc=false;
            for(Leader lead : leaderList){
                if(p.GetUserID().equals(lead.GetProfile().GetUserID())){
                    isLeader=true;
                    leader=lead;
                    leader.now=OffsetDateTime.now();
                    if(message.equalsIgnoreCase("filters")){
                        event.getChannel().sendMessageAsync(msg.info.FiltersAvailable(leaderFilters, isLeader),null);
                        return;
                    }
                    break;
                }
            }
            
            //
            
            if(!isLeader){
                for(Merc mercen : mercList){
                    if(p.GetUserID().equals(mercen.GetProfile().GetUserID())){
                        isMerc=true;
                        merc=mercen;
                        merc.now=OffsetDateTime.now();
                        if(message.equalsIgnoreCase("filters")){
                        event.getChannel().sendMessageAsync(msg.info.FiltersAvailable(mercFilters, isLeader),null);
                        return;
                        }
                        break;
                    }
                }
            }
            else{
                /*is a leader*/
                if(message.equalsIgnoreCase("search")){
                    //todo: search for mercs
                    String mercs="";
                    leader.mercList.clear();
                    List<Merc> deleteList = new ArrayList<>();
                    int number = 1;
                    for(Merc mercen: mercList){
                        if (((mercen.profile.rank<=leader.rankH && mercen.profile.rank>=leader.rankL) ||(mercen.huntRank<=leader.rankH && mercen.huntRank>=leader.rankL)) &&
                                (mercen.hunting.toLowerCase().replaceAll("\\s+","").contains(leader.lookingToHunt.toLowerCase().replaceAll("\\s+","")) ||
                                leader.lookingToHunt.toLowerCase().replaceAll("\\s+","").contains(mercen.hunting.toLowerCase().replaceAll("\\s+",""))) &&
                                (mercen.hasRole(leader.role) || leader.role.equals("")) &&
                                (mercen.hasWeapon(leader.weapon) || leader.weapon.equals("")) &&
                                (leader.huntTypeSearch.equals("") || leader.huntTypeSearch.equals(mercen.huntType))&&
                                mercen.requestFrom==null){
                            //check to make sure it isnt over 12 hours old first!
                            if(OffsetDateTime.now().isAfter(mercen.now.plusHours(4))){
                                deleteList.add(mercen);
                            }else{
                                //can be added(will use more search parameters later)
                                leader.mercList.add(mercen);
                                mercs+=msg.info.MercListing(number, event.getJDA().getUserById(mercen.GetProfile().GetUserID()).getUsername(), mercen);
                                number++;
                            }
                        }
                    }
                    //delete all over 4 hours
                    for (Iterator<Merc> it = deleteList.iterator(); it.hasNext();) {
                        Merc mercen = it.next();
                        mercList.remove(mercen);
                    }
                    if (mercs.equals("")){
                        event.getChannel().sendMessageAsync(msg.error.NoSearchResult(),null);
                        return;
                    }
                    String sendme = mercs + msg.response.RoomListAppendix();
                    ArrayList<String> messages = splitMessage(sendme);
                    
                    for(String s:messages){
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
                        if (listIndex>leader.mercList.size() || listIndex<=0){//not a viable index
                            event.getChannel().sendMessageAsync("use a number 1-"+Integer.toString(leader.mercList.size())+".",null);
                            return;
                        }
                        listIndex--;
                        Merc m = leader.mercList.get(listIndex);
                        if (m.requestFrom != null){
                            event.getChannel().sendMessageAsync("he already has a request pending from a leader, I cant ask him untill he answers that request.",null);
                            return;
                        }
                        m.requestFrom=leader;
                        event.getJDA().getUserById(m.profile.UserID).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(leader.profile.UserID).getUsername()+"__** would like you to hunt\n\""+leader.hunting+"\"\nwould you like to accept? (Y/N)",null);
                        event.getChannel().sendMessageAsync("Invitation has been sent to "+event.getJDA().getUserById(m.profile.UserID).getUsername(), null);
                    }catch(Exception e){
                        event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                    }
                    return;
                    
                }
                
                else if(message.toLowerCase().startsWith("slots ")){
                   try{
                       int s = Integer.parseInt(message.split(" ")[1]);
                       if (s<0||s>3){
                           event.getChannel().sendMessageAsync("you can only have 0-3 slots for hunters not including yourself **__"+event.getJDA().getUserById(p.UserID).getUsername()+"__**",null);
                           return;
                       }
                       leader.slots=s;
                       event.getChannel().sendMessageAsync("Slots in group changed to `"+leader.slots+"`",null);
                   }catch(Exception e){
                       event.getChannel().sendMessageAsync("MEOWCH, You are bad at math! Try using a number next time.",null);
                   }
                   return;
                }
                
                else if(message.equalsIgnoreCase("y")){
                    if (leader.requestFrom == null){
                        event.getChannel().sendMessageAsync("you have no requests waiting.",null);
                        return;
                    }
                    leader.slots--;
                    event.getChannel().sendMessageAsync("I sent **__"+event.getJDA().getUserById(leader.requestFrom.profile.UserID).getUsername()+"__** the hall info\n"
                            + "you have "+leader.slots+" slots left.",null);
                    event.getJDA().getUserById(leader.requestFrom.profile.UserID).getPrivateChannel().sendMessageAsync("Join **__"+event.getJDA().getUserById(leader.profile.UserID).getUsername()+"__**'s hunt!\n"
                            + "HallID:   "+leader.hallID+"\n"
                            + "Passcode: "+leader.passcode,null);
                    event.getJDA().getUserById(leader.requestFrom.profile.UserID).getPrivateChannel().sendMessageAsync("I have delisted you, happy hunting!",null);
                    mercList.remove(leader.requestFrom);
                    
                    leader.requestFrom=null;
                    return;
                }
                
                else if(message.equalsIgnoreCase("n")){
                    if (leader.requestFrom == null){
                        event.getChannel().sendMessageAsync("you have no requests waiting.",null);
                        return;
                    }
                    event.getChannel().sendMessageAsync("you declined **__"+event.getJDA().getUserById(leader.requestFrom.profile.UserID).getUsername()+"__**'s request",null);
                    event.getJDA().getUserById(leader.requestFrom.profile.UserID).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(leader.profile.UserID).getUsername()+"__** declined your request",null);
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
                        leader.rankL = leader.huntRank;
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
                    List<Leader> deleteList = new ArrayList<>();
                    for(Leader lead: leaderList){
                        if (lead.slots>0 &&
                                (merc.huntTypeSearch.equals("") || merc.huntTypeSearch.equals(lead.huntType)) &&
                                ((lead.profile.rank<=merc.rankH && lead.profile.rank>=merc.rankL) || (lead.huntRank<=merc.rankH && lead.huntRank>=merc.rankL)) &&
                                (lead.hunting.toLowerCase().replaceAll("\\s+","").contains(merc.lookingToHunt.toLowerCase().replaceAll("\\s+","")) ||
                                (merc.lookingToHunt.toLowerCase().replaceAll("\\s+","").contains(lead.hunting.toLowerCase().replaceAll("\\s+","")))) &&
                                (lead.requestFrom==null)){
                            //check to make sure it isnt over 4 hours old first!
                            if(OffsetDateTime.now().isAfter(lead.now.plusHours(4))){
                                deleteList.add(lead);
                            }else{
                                //can be added(will use more search parameters later)
                                merc.leaderList.add(lead);
                                leaders+=Integer.toString(number)+".    **__"+event.getJDA().getUserById(lead.profile.UserID).getUsername()
                                        +"__**  (Hunter rank:  `"+Integer.toString(lead.profile.rank)
                                        +"`)\n      hunting rank: `"+Integer.toString(lead.huntRank)
                                        +"`,  Description:  "+lead.hunting+"\n";
                                number++;
                            }
                        }
                    }
                    //delete all over 4 hours
                    for (Iterator<Leader> it = deleteList.iterator(); it.hasNext();) {
                        Leader lead = it.next();
                        leaderList.remove(lead);
                    }
                    
                    
                    if(leaders.equals("")){
                        event.getChannel().sendMessageAsync("I could Meownt find any groups like that",null);
                        return;
                    }
                    String sendme = leaders
                        + "`ask <X>` - asks a Hunter by number on your most recent search to join your room, or invite you to their room\n"
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
                        event.getJDA().getUserById(l.profile.UserID).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(merc.profile.UserID).getUsername()+"__** would like to hunt with you, would you like to accept? (Y/N)",null);
                        event.getChannel().sendMessageAsync("Invitation requested from "+event.getJDA().getUserById(l.profile.UserID).getUsername(),null);
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
                    event.getJDA().getUserById(merc.requestFrom.profile.UserID).getPrivateChannel().sendMessageAsync("I sent **__"+event.getJDA().getUserById(merc.profile.UserID).getUsername()+"__** the hall info\n"
                            + "you have "+merc.requestFrom.slots+" slots left",null);
                    event.getChannel().sendMessageAsync("Join **__"+event.getJDA().getUserById(merc.requestFrom.profile.UserID).getUsername()+"__**'s hunt!\n"
                            + "HallID:   "+merc.requestFrom.hallID+"\n"
                            + "Passcode: "+merc.requestFrom.passcode,null);
                    mercList.remove(merc);
                    event.getChannel().sendMessageAsync("I have delisted you, happy hunting!",null);
                    return;
                }
                
                else if(message.equalsIgnoreCase("n")){
                    if (merc.requestFrom == null){
                        event.getChannel().sendMessageAsync("you have no requests waiting.",null);
                        return;
                    }
                    event.getChannel().sendMessageAsync("you declined **__"+event.getJDA().getUserById(merc.requestFrom.profile.UserID).getUsername()+"__**'s request",null);
                    event.getJDA().getUserById(merc.requestFrom.profile.UserID).getPrivateChannel().sendMessageAsync("**__"+event.getJDA().getUserById(merc.profile.UserID).getUsername()+"__**declined your request",null);
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
            
            
            event.getChannel().sendMessageAsync("\uD83D\uDE40 I cant handle that right meow! \uD83D\uDE40\n"
                    + "if you need help, just type help.",null);
            
        }
    }
    
    
    Leader getLeaderObj(String userID){
        for(Leader lead: leaderList){
            if(lead.profile.UserID.equals(userID)){
                return lead;
            }
        }
        return null;
    }
    
    Merc getMercObj(String userID){
        for(Merc merc: mercList){
            if(merc.profile.UserID.equals(userID)){
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
            if(OffsetDateTime.now().isAfter(lead.now.plusHours(4)) || (myJDA.getUserById(lead.profile.UserID).getOnlineStatus() == OnlineStatus.OFFLINE) ){
                deleteList1.add(lead);
            }
        }
        leaderList.removeAll(deleteList1);
        
        for(Merc mercen: mercList){
            if(OffsetDateTime.now().isAfter(mercen.now.plusHours(4)) || (myJDA.getUserById(mercen.profile.UserID).getOnlineStatus() == OnlineStatus.OFFLINE)){
                deleteList2.add(mercen);
            }
        }
        mercList.removeAll(deleteList2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Neko().init();
        // TODO code application logic here
    }
    
    public void init() {
        Profiles.getInstance().read();
        try {
            new JDABuilder().addListener(this).setBotToken(token).setAudioEnabled(false).buildAsync();
        } catch (LoginException | IllegalArgumentException ex) {
            System.err.println("ERROR - Building JDA : "+ex.toString());
            System.exit(1);
        }
    }
    
}