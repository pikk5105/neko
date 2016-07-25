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
    private String UserID;
    private List<String> Weapons = new ArrayList<String>();
    private List<String> Roles = new ArrayList<String>();
    private HunterRank Rank = null;
    
    Profile(String UserId, String[] Weapons, String[] Roles, int rank)
    {
        this.UserID=UserId;
        this.Weapons.addAll(Arrays.asList(Weapons));
        this.Weapons.remove("");
        this.Roles.addAll(Arrays.asList(Roles));
        this.Roles.remove("");
        this.Rank = new HunterRank(rank);
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

    public List<String> GetWeaponList()
    {
        return Weapons;
    }

    void addRole(String role){
        if(Roles.contains(role)){
            return;
        }
        Roles.add(role);
    }

    public List<String> GetRoleList()
    {
        return Roles;
    }
    
    void removeRole(String role){
        Roles.remove(role);
    }

    public void SetUserID(String id)
    {
        UserID = id;
    }

    public String GetUserID()
    {
        return UserID;
    }

    public HunterRank GetHunterRank()
    {
        return Rank;
    }
}
