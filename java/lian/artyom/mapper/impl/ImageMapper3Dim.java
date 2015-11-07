package lian.artyom.mapper.impl;

import lian.artyom.mapper.ImageMapper;

import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.Result3D;
import static lian.artyom.RMethods.Tuple;

/**
 * TODO refactor later
 * method perform map reduce operation for chunk of image in multithread
 * Created by artem on 22.10.15.
 */
public class ImageMapper3Dim extends ImageMapper {

    private Result3D result;

    /**
     * this method performs mapReduce operation
     *
     * @return
     */
    protected void mapReduce() {

        Map<Tuple, Integer> map = new HashMap<>();

        for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                for (int m = 0; m < height; m++) {
                    if (m == j) continue;
                    for (int n = 0; n < width; n++) {
                        if (n == i) continue;
                        Tuple t = new Tuple(image[i][j], image[i][m], image[n][j]);
                        if (map.containsKey(t)) {
                            map.put(t, map.get(t) + 1); // increasing tuple counter by 1
                        } else {
                            map.put(t, 1); // putting new value to map
                        }
                    }
                }
            }

        }

        result = new Result3D(map.entrySet().size());
        int i = 0;
        for (Map.Entry entry : map.entrySet()) {
            result.tuples[i] = (Tuple) entry.getKey();
            result.values[i++] = (Integer) entry.getValue();
        }
        System.out.println("mapReduce:packing results:total number" + map.entrySet().size());
    }

    @Override
    public void run() {
        mapReduce();
    }

    private int startX, startY, size;

    public ImageMapper3Dim(int startX, int startY, int size, int[][] image, int height, int width) {
        this.startX = startX;
        this.startY = startY;
        this.size = size;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Result3D getResult() {
        return result;
    }
}
