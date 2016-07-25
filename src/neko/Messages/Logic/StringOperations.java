package neko.Messages.Logic;

/**
 * Created by Dominik on 25.07.2016.
 */
public class StringOperations
{
    public static String AppendStringArray(String target, String[] source)
    {
        for(String str : source)
        {
            target += str + "\n";
        }
        return target;
    }
}
