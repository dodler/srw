package lian.artyom;

import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.Result;

/**
 * class that perform merge of two resuls of mapping
 * mapping is implemented in ImageMapper3Dim
 * result is stored in private varuable, which can be accessed by getter method
 * should be instansiated only via 2-args constructor
 * TODO migrate to R
 * Created by artem on 22.10.15.
 */
public class SimpleMapMerger {
    private SimpleMapMerger() {

    }

    /**
     * target is merge of both - source and target
     */
    private Result target, source;

    public SimpleMapMerger(Result source, Result target) {
        this.source = source;
        this.target = target;
        prepareMerge();
    }

    public void setTarget(Result target) {
        this.target = target;
    }

    public Result getResult() {
        return source;
    }

    private Map<Integer, Integer> map;// = new HashMap<>();

    /**
     * this method fills map of objects with given source result
     */
    public void prepareMerge() {
        map = new HashMap<>();
        for (int i = 0; i < source.key.length; i++) {
            map.put(source.key[i], source.value[i]);
        }
    }

    /**
     * this method merges target result into source, note, that at first you should launch prepareMerge method,
     * otherwise a npe will be thrown
     */
    public void merge() {
        for (int i = 0; i < target.key.length; i++) {
            if (map.containsKey(target.key[i])) {
                // incrementing by value from target map
                map.put(target.key[i], map.get(target.key[i]) + target.value[i]);
            } else {
                // putting new value
                map.put(target.key[i], target.value[i]);
            }
        }

        int i = 0;
        source = new Result(map.entrySet().size());
        for (Map.Entry entry : map.entrySet()) {
            source.key[i] = (Integer) entry.getKey();
            source.value[i++] = (Integer) entry.getValue();
        }
        prepareMerge();
        System.out.println("merge finished:total number:" + source.key.length);
    }
}
