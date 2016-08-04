/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neko;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 *
 * @author Brady
 */
public abstract class Command
{
    private String name="";
    private String command="";
    private String[] args;
    private boolean privateCommand;
    
    Command(String name, String command, boolean privateCommand, String[] args)
    {
        this.name = name;
        this.command = command;
        this.privateCommand = privateCommand;
        this.args = args;

    }

    public abstract void Run();
    public abstract boolean Exec();
}