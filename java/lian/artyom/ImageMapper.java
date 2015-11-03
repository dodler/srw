package lian.artyom;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.*;

/**
 * TODO refactor later
 * method perform map reduce operation for chunk of image in multithread
 * Created by artem on 22.10.15.
 */
public class ImageMapper extends Thread
{


    /**
     * image to map
     */
    private BufferedImage image;

    private Result3D result;

    /**
     * this method performs mapReduce operation
     *
     * @return
     */
    private Result3D mapReduce()
    {
        Map<Tuple, Integer> map = new HashMap<>();

        for (int i = 0; i < image.getWidth(); i++)
        {
            for (int j = 0; j < image.getHeight(); j++)
            {
                for (int m = j; m < image.getHeight(); m++)
                {
		    if (m == j) continue;
                    for (int n = i; n < image.getWidth(); n++)
                    {
			if (n==i) continue;
                        Tuple t = new Tuple(image.getRGB(i, j), image.getRGB(i,m), image.getRGB(n,j));
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

        Result3D result = new Result3D(map.entrySet().size());
        int i = 0;
        for (Map.Entry entry : map.entrySet())
        {
            result.tuples[i] = (Tuple) entry.getKey();
            result.values[i++] = (Integer) entry.getValue();
        }
        System.out.println("mapReduce:packing results:total number" + map.entrySet().size());
        return result;
    }

    private ImageMapper()
    {

    }

    public ImageMapper(BufferedImage image)
    {
        this.image = image;
    }

    @Override
    public void run()
    {
        System.out.println("Thread#" + this.getId() + "started.");
        result = mapReduce();
    }

    public Result3D getResult()
    {
        return result;
    }
}
