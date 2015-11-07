package lian.artyom.mapper.impl;

import lian.artyom.RMethods;
import lian.artyom.mapper.ImageMapper;

import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.Result3D;

/**
 * TODO refactor later
 * method perform map reduce operation for chunk of image in multithread
 * Created by artem on 22.10.15.
 */
public class ImageMapper1Dim extends ImageMapper {

    private Result3D result;

    /**
     * this method performs mapReduce operation
     *
     * @return
     */
    protected void mapReduce() {

        Map<RMethods.Tuple, Integer> map = new HashMap<>();

        for (int i = 0; i < width; i++) {
            for (int j = startY; j < startY + size; j++) {
                RMethods.Tuple t = new RMethods.Tuple(image[i][j], 0, 0);
                if (map.containsKey(t)) {
                    map.put(t, map.get(t) + 1); // increasing tuple counter by 1
                } else {
                    map.put(t, 1); // putting new value to map
                }
            }

        }

        result = new RMethods.Result3D(map.entrySet().size());
        int i = 0;
        for (Map.Entry entry : map.entrySet()) {
            result.tuples[i] = (RMethods.Tuple) entry.getKey();
            result.values[i++] = (Integer) entry.getValue();
        }
        System.out.println("mapReduce:packing results:total number" + result.tuples.length);
    }

    @Override
    public void run() {
        mapReduce();
    }

    public Result3D getResult() {
        return this.result;
    }

    private int startX, startY, size;

    public ImageMapper1Dim(int startX, int startY, int size, int[][] image, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.size = size;
        this.image = image;
        this.width = width;
        this.height = height;
    }
}
