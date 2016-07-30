package neko;

/**
 * Created by Dominik on 21.07.2016.
 */
public class Filters
{
    public enum Roles
    {
        Damage,
        Tank,
        Status,
        Support,
        Mounter,
        Other
    }

    public enum Weapons
    {
        GS,
        LS,
        SnS,
        DB,
        Hammer,
        HH,
        Lance,
        GL,
        SA,
        CB,
        IG,
        LBG,
        HBG,
        Bow,
        Prowler
    }

    public enum HuntType
    {
        Low,
        High,
        Deviant}

    private int rankH             = 999;
    private int rankL             = 1;
    private int slotsOpen         = 3;

    private String weapon         = "";
    private String role           = "";
    private String monster        = "";
    private String huntType       = "";     // high low deviant

    public void SetRankLimitUpper(int rank)
    {
        rankH = rank;
    }

    public int GetRankLimitUpper()
    {
        return rankH;
    }

    public void SetRankLimitLower(int rank)
    {
        rankL = rank;
    }

    public int GetRankLimitLowerLower()
    {
        return rankL;
    }

    public void SetSlotsOpenRequirement(int slots)
    {
        slotsOpen = slots;
    }

    public int GetSlotsOpenRequirement()
    {
        return slotsOpen;
    }

    public void SetWeaponFilter(Weapons weapon)
    {
       switch(weapon)
       {

           case GS:
               this.weapon = "Great Sword";
               break;
           case LS:
               this.weapon = "Long Sword";
               break;
           case SnS:
               this.weapon = "Sword & Shield";
               break;
           case DB:
               this.weapon = "Dual Blades";
               break;
           case Hammer:
               this.weapon = "Hammer";
               break;
           case HH:
               this.weapon = "Hunting Horn";
               break;
           case Lance:
               this.weapon = "Lance";
               break;
           case GL:
               this.weapon = "Gunlance";
               break;
           case SA:
               this.weapon = "Switch Axe";
               break;
           case CB:
               this.weapon = "Charge Blade";
               break;
           case IG:
               this.weapon = "Insect Gleive";
               break;
           case LBG:
               this.weapon = "Light Bowgun";
               break;
           case HBG:
               this.weapon = "Heavy Bowgun";
               break;
           case Bow:
               this.weapon = "Bow";
               break;
           case Prowler:
               this.weapon = "Prowler";
               break;
       }
    }

    public String GetWeaponFilter()
    {
        return weapon;
    }

    public void SetRoleFilter(Roles role)
    {
        switch (role) {
            case Damage:
                this.role = "Damage";
                break;
            case Tank:
                this.role = "Tank";
                break;
            case Status:
                this.role = "Status";
                break;
            case Support:
                this.role = "Support";
                break;
            case Mounter:
                this.role = "Mounter";
                break;
            case Other:
                this.role = "Other";
                break;
        }
    }

    public String GetRoleFilter()
    {
        return role;
    }

    public void SetMonster(String monster)
    {
        this.monster = monster;
    }

    public String GetMonster()
    {
        return monster;
    }

    public void SetHuntType(HuntType type)
    {
        switch (type)
        {
            case Low:
                this.huntType = "Low Rank";
                break;
            case High:
                this.huntType = "High Rank";
                break;
            case Deviant:
                this.huntType = "Deviant";
                break;
        }
    }

    public String GetHuntType()
    {
        return huntType;
    }
}
