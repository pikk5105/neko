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
        while(GetFinishState())
        {
            switch(GetStep())
            {
                case 0:
                {
                    //TODO Greeting
                    Exec();
                    SetStep(1);
                }
                case 1:
                {
                    //TODO Weapons Setup
                    Exec();
                    SetStep(2);
                }
                case 2:
                {
                    //TODO Roles Setup
                    Exec();
                    SetStep(3);
                }
                case 3:
                {
                    //TODO Hunter Rank
                    Exec();
                    SetFinishState(true);
                }
            }
            if(GetFinishState())
            {
                boolean accepted = false;

                //TODO Display input and ask if correct

                if(!accepted)
                {
                    SetFinishState(false);
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
