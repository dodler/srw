package lian.artyom.mapper.impl;

import lian.artyom.RMethods;
import lian.artyom.mapper.ImageMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dodler on 05.11.15.
 */
public class ImageMapper2Dim extends ImageMapper {

    private RMethods.Result3D result;

    /**
     * this method performs mapReduce operation
     *
     * @return
     */
    protected void mapReduce() {
        Map<RMethods.Tuple, Integer> map = new HashMap<>();

        for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                for (int n = 0; n < width; n++) {
                    if (n == i) continue;
                    RMethods.Tuple t = new RMethods.Tuple(image[i][j], image[n][j], 0);
                    if (map.containsKey(t)) {
                        map.put(t, map.get(t) + 1); // increasing tuple counter by 1
                    } else {
                        map.put(t, 1); // putting new value to map
                    }
                }
            }

        }

        result = new RMethods.Result3D(map.entrySet().size());
        int i = 0;
        for (Map.Entry entry : map.entrySet()) {
            result.tuples[i] = (RMethods.Tuple) entry.getKey();
            result.values[i++] = (Integer) entry.getValue();
        }
    }

    @Override
    public void run() {
        mapReduce();
    }

    public RMethods.Result3D getResult() {
        return this.result;
    }

    private int startX, startY, size;

    public ImageMapper2Dim(int startX, int startY, int size, int[][] image, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.size = size;
        this.image = image;
        this.width = width;
        this.height = height;
    }

}
