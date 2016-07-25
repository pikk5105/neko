package neko;

import neko.Messages.*;
import neko.Messages.Error;

/**
 * Created by Dominik on 21.07.2016.
 */
public class MessageHandler {
    private MessageHandler instance;

    public Error error;
    public General general;
    public Information info;
    public Interaction interaction;
    public Response response;
    public Success success;

    private MessageHandler() {
        init();
    }

    public MessageHandler GetInstance() {
        if (instance == null) {
            instance = new MessageHandler();
        }
        return instance;
    }

    private void init() {
        error = new Error();
        general = new General();
        info = new Information();
        interaction = new Interaction();
        response = new Response();
        success = new Success();
    }
}