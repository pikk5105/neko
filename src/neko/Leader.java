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
public class Leader {
    Profile profile=null;
    int huntRank=0;
    String huntType="";//high low diviant
    String hunting="";
    String hallID="";
    String passcode="";
    
    OffsetDateTime now=OffsetDateTime.now();
    
    //search filters
    int rankH=999;
    int rankL=1;
    int slots=3;
    String weapon="";
    String role="";
    String lookingToHunt="";
    String huntTypeSearch = "";// high low deviant
    
    List<Merc> mercList = new ArrayList<>();//list from search
    Merc requestFrom = null;
    
    Leader(Profile profile, int huntRank, String hunting, String hallID, String passcode, boolean Deviant){
        this.profile=profile;
        this.huntRank=huntRank;
        if (huntRank>4){
            this.huntType="High";
        }else{
            this.huntType="Low";
        }
        this.rankL=huntRank;
        this.hunting=hunting;
        this.hallID=hallID;
        this.passcode=passcode;
        if (Deviant==true){
            this.huntType="Deviant";
        }
    }
    
}
