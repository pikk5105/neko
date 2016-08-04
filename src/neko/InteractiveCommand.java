package neko;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by Dominik on 04.08.2016.
 */
public abstract class InteractiveCommand extends Command
{
    private int step = 0;

    public InteractiveCommand(String name, String command, boolean privateCommand, String[] args)
    {
        super(name, command, privateCommand, args);
    }

    public void SetStep(int step)
    {
        this.step = step;
    }

    public void IncStep()
    {
        step++;
    }

    public int GetStep()
    {
        return step;
    }

    public String ReadMessage(MessageReceivedEvent event)
    {
        return event.getMessage().getRawContent();
    }
}
