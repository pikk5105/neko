package neko;

import java.util.*;

/**
 * Created by Dominik on 21.07.2016.
 */
public class QueueHandler
{
    private HashMap<String, Queue> queues;

    public QueueHandler()
    {
        queues = new HashMap<String, Queue>();
    }

    public Queue GetQueue(String queueName)
    {
        return queues.get(queueName);
    }

    public void AddQueueObject(String key, Queue queue)
    {
        queues.put(key, queue);
    }

    public void RemoveQueueObject(Queue queue)
    {

    }

    //  TODO: Add Queue Manipulation Logic


}
