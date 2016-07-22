package neko;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 21.07.2016.
 */
public class Queue
{
    List<Merc> mercenaries;

    List<Leader> leaders;

    public Queue()
    {
        mercenaries = new ArrayList<Merc>();
        leaders     = new ArrayList<Leader>();
    }
}
