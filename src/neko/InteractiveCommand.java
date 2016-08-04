package neko;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by Dominik on 04.08.2016.
 */
public abstract class InteractiveCommand extends Command
{
    private int step = 0;
    private boolean finished = false;
    private String lastMessage;

    public InteractiveCommand(String name, String command, boolean privateCommand, String[] args)
    {
        super(name, command, privateCommand, args);
        this.lastMessage = "";
    }

    public void SetStep(int step)
    {
        this.step = step;
    }

    public int GetStep()
    {
        return step;
    }

    public void SetFinishState(boolean state)
    {
        this.finished = state;
    }

    public boolean GetFinishState()
    {
        return finished;
    }

    public String ReadMessage(MessageReceivedEvent event)
    {
        return event.getMessage().getRawContent();
    }
}
