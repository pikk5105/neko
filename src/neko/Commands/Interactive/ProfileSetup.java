package neko.Commands.Interactive;

import neko.InteractiveCommand;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by Dominik on 04.08.2016.
 */
public class ProfileSetup extends InteractiveCommand
{
    ProfileSetup(String name, String command, boolean privateCommand, String[] args)
    {
        super(name, command, privateCommand, args);
    }

    @Override
    public void Run()
    {
        switch (GetStep())
        {
            case 0:
            {
                //TODO Greeting
                if(Exec())
                {
                    IncStep();
                }
            }
            case 1:
            {
                //TODO Weapons Setup
                if(Exec())
                {
                    IncStep();
                }
            }
            case 2:
            {
                //TODO Roles Setup
                if(Exec())
                {
                    IncStep();
                }
            }
            case 3:
            {
                //TODO Hunter Rank
                if(Exec())
                {
                    IncStep();
                }
            }
        }
    }

    @Override
    public boolean Exec()
    {
        switch(GetStep())
        {
            case 1:
            {
                //TODO Write Weapons to profile
                return true;
            }
            case 2:
            {
                //TODO Write Roles to profile
                return true;
            }
            case 3:
            {
                //TODO Write Hunter Rank to profile
                return true;
            }
            default:
            {
                return false;
            }
        }
    }
}
