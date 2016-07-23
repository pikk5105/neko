package neko;

/**
 * Created by Dominik on 23.07.2016.
 */
public class Help
{
    //  TODO: This class will handle the help messages as well as probably soon to be an interactive setup process

    public final String helpMessage1    =   "here are some catmands I can respmoewnd to.\n"
            +   "just send me a purrsonal meowsage to talk to me.\n"
            +   "`help` - See all the ameowzing things I can do\n\n"
            +   "profile setup commands:\n"
            +   "`add <X>` - Adds X to your roles or weapons\n"
            +   "`remove <X>` - removes X from your roles or weapons\n"
            +   "`roles` - Lists the current hunter roles I recognize\n"
            +   "`myRoles` - Lists the roles you are willing to perform on a hunt, use the add command to add to it\n"
            +   "`weapons` - Lists the weapons I recognize\n"
            +    "`myWeapons` - Lists the weapons you like to use, use the add command to add to it\n"
            +   "`myRank` - your online HR in Meownster Hunter Generations\n"
            +   "`rankUp` - moves your HR up by one, Nyaxelent job!\n"
            +   "`changeRank <X>` - changes your HR to X, useful for a if you forget to use rankup a few times.\n\n";

    public final String helpMessage2    =   "Hunter finding commands:\n"
            +   "`lookup <name>` - looks for the profile of a hunter.\n"
            +   "`Hunters` - I'll tell you how many mercenaries and leaders are currently looking for Hunters.\n"
            +   "`enlist merc <Rank of Quest> <looking to hunt>` - you will be added to the list of meownster Hunters looking for a group.\n"
            +   "                    Rank of Quest is an HR number 1-999 (or d# for deviant hunts) and looking to hunt can be anything\n"
            +   "`enlist leader <HallID> <Passcode> <Rank of Quest> <Hunting>` - your room will be added to my Meownster Hunter group List.\n"
            +   "                    Rank of Quest is an HR number 1-999(or d# for deviant hunts) and looking to hunt can be anything\n"
            +   "`delist` - unenlists you\n\n"
            +   "Cammands to use while enlisted:\n"
            +   "`filters` - lists all filters that you can currently use to find Hunters.\n"
            +   "`addFilter <filter> <X>` - filters hunters with X\n"
            +   "`removeFilter <filter>` - clears that filter field\n"
            +   "`search` - executes a search using your added filters. Will show all results if you do not have any filters.\n"
            +   "`ask <X>` - asks a Hunter by number on your most recent search to join your room, or invite you to their room\n"
            +   "`spots <X>` - If you are a leader, it will let others know how many spots you have left.\n"
            +   "                    a group with 0 spots will not show up in searches\n\n"
            +   "Commands that can be used in Monster Hunter Gathering Hall channels:\n"
            +   "`/neko lookup <X>` - looks for a hunter with the name X and displays basic info of them\n"
            +   "`/neko myhunt` - if you are enlisted at a leader I will put your hall info in the chat you asked me in";

}
