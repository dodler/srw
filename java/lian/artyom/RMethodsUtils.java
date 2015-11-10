package lian.artyom;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by artem on 10.11.15.
 */
public class RMethodsUtils
{

    public static RMethods.Result3D sumResult3D(RMethods.Result3D result)
    {
        Map<RMethods.Tuple, Integer> preFinalResult = new HashMap<>();
        for (int i = 0; i < result.values.length; i++)
        {
            result.tuples[i].y = 0;
            if (preFinalResult.containsKey(result.tuples[i]))
            {
                preFinalResult.put(result.tuples[i], preFinalResult.get(result.tuples[i]) + result.values[i]);
            } else
            {
                preFinalResult.put(result.tuples[i], result.values[i]);
            }
        }
        Map<RMethods.Tuple, Integer> finalResult = new HashMap<>();
        for (Map.Entry<RMethods.Tuple, Integer> entry : preFinalResult.entrySet())
        {
            entry.getKey().x = 0;
            if (finalResult.containsKey(entry.getKey()))
            {
                finalResult.put(entry.getKey(), finalResult.get(entry.getKey()) + entry.getValue());
            } else
            {
                finalResult.put(entry.getKey(), entry.getValue());
            }
        }

        RMethods.Result3D finalResult3D = new RMethods.Result3D(finalResult.size());
        int i = 0;
        for (Map.Entry<RMethods.Tuple, Integer> entry : preFinalResult.entrySet())
        {
            finalResult3D.tuples[i] = entry.getKey();
            finalResult3D.values[i++] = entry.getValue()/4;
        }
        return result;
    }
}
