/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Brady
 */
public class Profile {
    String UserID;
    List<String> Weapons = new ArrayList<String>();
    List<String> Roles = new ArrayList<String>();
    int rank;
    
    Profile(String UserId, String[] Weapons, String[] Roles, int rank){
        this.UserID=UserId;
        this.Weapons.addAll(Arrays.asList(Weapons));
        this.Weapons.remove("");
        this.Roles.addAll(Arrays.asList(Roles));
        this.Roles.remove("");
        this.rank=rank;
    }
    void addWeapon(String weapon){
        if(Weapons.contains(weapon)){
            return;
        }
        Weapons.add(weapon);
    }
    
    void removeWeapon(String weapon){
        Weapons.remove(weapon);
    }
    
    void addRole(String role){
        if(Roles.contains(role)){
            return;
        }
        Roles.add(role);
    }
    
    void removeRole(String role){
        Roles.remove(role);
    }
}
