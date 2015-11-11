package lian.artyom.mapper.impl;

import lian.artyom.RMethods;
import lian.artyom.RMethodsUtils;
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
public class ImageMapper3Dim extends ImageMapper
{

    private Result3D result;

    /**
     * this method performs mapReduce operation
     *
     * @return
     */
    public void mapReduce()
    {
        Map<Tuple, Integer> map = new HashMap<>();

        for (int i = 0; i < width; i++)
        {
            for (int j = startY; j < startY + size; j++)
            {
                for (int m = 0; m < height; m++)
                {
                    if (m == j) continue;
                    for (int n = 0; n < width; n++)
                    {
                        if (n == i) continue;
                        Tuple t = new Tuple(image[i][j], image[i][m], image[n][j]);
                        if (map.containsKey(t))
                        {
                            map.put(t, map.get(t) + 1); // increasing tuple counter by 1
                        } else
                        {
                            map.put(t, 1); // putting new value to map
                        }
                    }
                }
            }

        }

        result = new Result3D(map.entrySet().size());
        int i = 0;
        for (Map.Entry entry : map.entrySet())
        {
            result.tuples[i] = (Tuple) entry.getKey();
            result.values[i++] = (Integer) entry.getValue();
        }
    }

    @Override
    public Result3D getResult()
    {
        return this.result;
    }

    @Override
    public void run()
    {
        mapReduce();
    }

    private int startX, startY, size;

    private ImageMapper3Dim()
    {
    }

    ;

    /**
     * only available contructor
     * warning, if height and width parameters or int do not correspond to real image,
     * u will recieve runtime exception, array out of bounds or something like this
     *
     * @param startX x point, where image processing starts from
     * @param startY y point, where image processing starts from
     * @param size   number of pixels by axy to be processed
     * @param image  source image packed in integer 2dim array
     * @param height height of image, can be extracted from Image type
     * @param width  width of image
     */
    public ImageMapper3Dim(int startX, int startY, int size, int[][] image, int width, int height)
    {
        this.startX = startX;
        this.startY = startY;
        this.size = size;
        this.image = image;
        this.width = width;
        this.height = height;
    }
}
