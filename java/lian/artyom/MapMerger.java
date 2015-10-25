package lian.artyom;

import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.*;

/**
 * class that perform merge of two resuls of mapping
 * mapping is implemented in ImageMapper
 * result is stored in private varuable, which can be accessed by getter method
 * should be instansiated only via 2-args constructor
 * TODO migrate to R
 * Created by artem on 22.10.15.
 */
public class MapMerger
{
    private MapMerger()
    {

    }

    /**
     * target is merge of both - source and target
     */
    private Result3D target, source, result;

    public MapMerger(Result3D source, Result3D target)
    {
        this.source = source;
        this.target = target;
    }

    public void setTarget(Result3D target)
    {
        this.target = target;
    }

    public Result3D getResult()
    {
        return result;
    }

    public void merge()
    {
        Map<Tuple, Integer> map = new HashMap<>();
        for (int i = 0; i < source.tuples.length; i++)
        {
            map.put(source.tuples[i], source.values[i]);
        }

        for (int i = 0; i < target.tuples.length; i++)
        {
            if (map.containsKey(target.tuples[i]))
            {
                // incrementing by value from target map
                map.put(target.tuples[i], map.get(target.tuples[i]) + target.values[i]);
            } else
            {
                // putting new value
                map.put(target.tuples[i], target.values[i]);
            }
        }

        Result3D result = new Result3D(map.entrySet().size());
        int i = 0;
        for (Map.Entry entry : map.entrySet())
        {
            result.tuples[i] = (Tuple) entry.getKey();
            result.values[i] = (Integer) entry.getValue();
        }

        this.result = result;
    }
}
