/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brady
 */
public class Merc {
    Profile profile;
    int huntRank=0;
    String hunting="";
    String huntType="";
    
    OffsetDateTime now=OffsetDateTime.now();
    
    //search filters
    int rankH=999;
    int rankL=1;
    int huntRankSearch=-1;
    String huntTypeSearch="";//Low High or Deviant
    /*
    String weapon="";
    String role="";
    */
    String lookingToHunt="";
    List<Leader> leaderList = new ArrayList<>();
    Leader requestFrom = null;
    
    Merc(Profile profile, int huntRank, String hunting, boolean Deviant){
        this.profile=profile;
        this.huntRank=huntRank;
        if (huntRank>4){
            this.huntType="High";
        }else{
            this.huntType="Low";
        }
        
        if (Deviant==true){
            this.huntType="Deviant";
        }
        this.hunting=hunting;
    }
    
    boolean hasRole(String role){
        for(String myRole : profile.Roles){
            if (myRole.equalsIgnoreCase(role)){
                return true;
            }
        }
        
        return false;
    }
    
    boolean hasWeapon(String weapon){
        for(String myWeapon : profile.Weapons){
        for(String myWeapon : profile.GetWeaponList()){
            if (myWeapon.equalsIgnoreCase(weapon)){
                return true;
            }
        }
        
        return false;
    }
}
