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
    private Result3D target, source;

    public MapMerger(Result3D source, Result3D target)
    {
        this.source = source;
        this.target = target;
	prepareMerge();
    }

    public void setTarget(Result3D target)
    {
        this.target = target;
    }

    public Result3D getResult()
    {
        return source;
    }
   
    private Map<Tuple, Integer> map;// = new HashMap<>();
 
    /** this method fills map of objects with given source result
    */
    public void prepareMerge()
    {	
	map = new HashMap<>();
        for (int i = 0; i < source.tuples.length; i++)
	{
	    map.put(source.tuples[i], source.values[i]);
	}
    }

    /**
    * this method merges target result into source, note, that at first you should launch prepareMerge method, 
    * otherwise a npe will be thrown
    */
    public void merge()
    {
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

        int i = 0;
	source = new Result3D(map.entrySet().size());
        for (Map.Entry entry : map.entrySet())
        {
            source.tuples[i] = (Tuple) entry.getKey();
            source.values[i++] = (Integer) entry.getValue();
        }
	prepareMerge();
	System.out.println("merge finished:total number:" + source.tuples.length);
    }
}
