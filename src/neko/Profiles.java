/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;

/**
 *
 * @author Brady
 */
public class Profiles extends DataSource{
    private static final Profiles profiles = new Profiles();
    
    final public static int USERID   = 0;
    final public static int WEAPONS = 1;
    final public static int ROLES  = 2;
    final public static int RANK = 3;
    
    private Profiles(){
        filename = "Neko_Profiles.txt";
        size = 4;//look at this later
        generateKey = (item)->{return item[USERID];};
    } 
    
    public static Profiles getInstance(){
        return profiles;
    }
    
    public Profile getProfile(String UserId){
        String[] profile = get(UserId);
        if (profile == null){
            return null;
        }
        String[] weaps = profile[WEAPONS].split("\\s+");
        String[] roles = profile[ROLES].split("\\s+");
        //make a profile obj
        Profile p = new Profile(UserId, weaps, roles, Integer.parseInt(profile[RANK]));
        
        return p;
        
    }
    
    public void setProfile(Profile p){
        //convert p to profile
        String weap="";
        String roles="";
        for(String str : p.GetWeaponList()){
            weap+=str+" ";
        }
        for(String str : p.GetRoleList()){
            roles+=str+" ";
        }
        String[] profile = {p.GetUserID(), weap, roles, Integer.toString(p.GetHunterRank().GetRank()) };
        
        set(profile);
    }
}
