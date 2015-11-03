package lian.artyom;

import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Override;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by artem on 27.09.15.
 */
public class RMethods
{
    /**
     * class that contains data after mapping data in picture
     * key - value of color
     * value - number of entering of key in pic
     */
    public static final class Result
    {
        public int[] key, value;
    }

    ;

    /**
     * class that contains one color tuple for 3dim histogram
     */
    public static final class Tuple extends Object
    {
        int x, y, z; // here z is current color, z - neighbour by ax, y - neighbour - by ay, value is number of this color

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;

            result = prime * result + z;
            result = prime * result + x;
            result = prime * result + y;

//            System.out.println("hash return:" + result);
            return result;
        }

        private EqualityOperation operation;

        @Override
        public boolean equals(Object tuple)
        {
            if (!tuple.getClass().equals(Tuple.class)) return false;

            return operation.equals(this, tuple);
        }

        public void setOperation(EqualityOperation operation)
        {
            this.operation = operation;
        }

        public Tuple()
        {
            this.operation = CHECK_COORDINATES_WITH_ORDER;
        }

        public static final EqualityOperation CHECK_COORDINATES_WITHOUT_ORDER = new EqualityOperation()
        { // default check operation checks if all coordinates are equal
            // order of coordinates should be equal
            @Override
            public boolean equals(Object tuple1, Object tuple2)
            {
                Tuple t1, t2;
                try
                {
                    t1 = (Tuple) tuple1;
                    t2 = (Tuple) tuple2;
                } catch (ClassCastException e)
                {
                    return false;
                }
                return (t1.z == t2.z);
            }
        };

        public static final EqualityOperation CHECK_COORDINATES_WITH_ORDER = new EqualityOperation()
        { // default check operation checks if all coordinates are equal
            // order of coordinates should be equal
            @Override
            public boolean equals(Object tuple1, Object tuple2)
            {
                Tuple t1, t2;
                try
                {
                    t1 = (Tuple) tuple1;
                    t2 = (Tuple) tuple2;
                } catch (ClassCastException e)
                {
                    return false;
                }
                return ((t1.z == t2.z) && (t1.x==t2.x) && (t1.y == t2.y));
            }
        };

        public Tuple(int z, int x, int y)
        {
            this();
            this.z = z;
            this.x = x;
            this.y = y;
        }

        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(z);
            sb.append(":");
            sb.append(x);
            sb.append(":");
            sb.append(y);
            sb.append(")");
            return sb.toString();
        }
    }

    ;

    public static void testTupleHash()
    {
        Tuple x1 = new Tuple(0, 0, 0);
        Tuple x2 = new Tuple(0, 1, 0);
        Tuple x3 = new Tuple(1, 3, 0);
        Tuple x5 = new Tuple(1, 3, 4);
        Tuple x4 = new Tuple(0, 0, 0);

        Map testMap = new HashMap<Tuple, Integer>(5);
        testMap.put(x1, 1);

        System.out.println("map contains x4:");
        System.out.println(testMap.containsKey(x4));
        System.out.println("map contains x1:");
        System.out.println(testMap.containsKey(x1));

    }

    /**
     * container for tuples
     */
    public static final class Result3D
    {
        Tuple[] tuples;
        int[] values;

        private Result3D()
        {

        }

        public Result3D(int length)
        {
            tuples = new Tuple[length];
            values = new int[length];
        }
    }

    public RMethods()
    {

    }

    public static void main(String[] args)
    {
        //String img = "../pic/test1.bmp";
        //String img = "../pic/test_double.bmp";
        String img = "/home/dodler/Документы/hist[R]/srw/java/pic/test_mono_2.png";
        int length = 1;
        build3DimHist(img, length);

    }

    public static void testStatic(int[] valus)
    {
        System.out.println(new Random().nextGaussian());
    }

    public static String className(Object object)
    {
        System.out.println(object.getClass().getDeclaredMethods());
        for (Method m : object.getClass().getDeclaredMethods())
        {
            System.out.println(m.getName());
        }
        return object.getClass().getName();
    }

    /**
     * method merges two maps
     * written to support r
     */
    public static Result mergeMaps(int[] key1, int[] value1, int[] key2, int[] value2)
    {
        Map<Integer, Integer> result = new HashMap<>(key1.length + 1);
        for (int i = 0; i < key1.length; i++)
        {
            if (result.containsKey(key1[i]))
            {// increment by key from key1
                result.put(key1[i], result.get(key1[i]) + value1[i]);
//                System.out.println("increment by key 1");
            } else
            {// add new key
//                System.out.println("insert key 1");
                result.put(key1[i], value1[i]);
            }
        }
        for (int i = 0; i < key2.length; i++)
        {
            if (result.containsKey(key2[i]))
            {// increment by key from key1
//                System.out.println("increment by key 2");
                result.put(key2[i], result.get(key2[i]) + value2[i]);
            } else
            {// add new key
//                System.out.println("insert key 2");
                result.put(key2[i], value2[i]);
            }
        }
        Result result1 = new Result();
        result1.key = new int[result.entrySet().size()];
        result1.value = new int[result.entrySet().size()];
//        System.out.println(result.entrySet().size());
        int i = 0;
        for (Map.Entry<Integer, Integer> e : result.entrySet())
        {
            result1.key[i] = e.getKey();
            result1.value[i++] = e.getValue();
        }


//        System.out.println("success");
//        DOMConfigurator.configure("/home/artem/IdeaProjects/Custom/config/log4j-config.xml");
//        log.debug("success");
//        log.trace("success");
//        result.putAll(m1);
//        result.putAll(m2);
//        return result;
        return result1;
    }

    public static String getName(int i, String dir, String name, String format, String delimiter)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(dir);
        sb.append("/");
        sb.append(name);
        sb.append(delimiter);
        sb.append(i);
        sb.append(".");
        sb.append(format);
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * method splits source image several rectangular parts
     *
     * @param img    source image root
     * @param dir    directory where output data will bewritten
     * @param name   name of output file chunk
     * @param format format of output chunk
     * @param length number of chunks to be craeted
     */
    public static void cropAndHandleImage(String img, String dir, String name, String format, int length)
    {
        try
        {
            int size = 0;
            BufferedImage image = ImageIO.read(new File(img));

            size = (int) image.getHeight() / length - 1;

            for (int i = 0; i < length; i++)
            {
//                ImageIO.write(image.getSubimage(0, i* size, image.getWidth(), size), "bmp",new File(getName(i)));
                BufferedImage img1 = image.getSubimage(0, i * size, image.getWidth(), size);
                long cnt = 0;
//                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(getName(i + 1, "pic/output ", " .csv", "# "))))
                File file = new File(getName(i + 1, dir, name, format, "#"));
                if (!file.exists())
                {
                    file.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("X" + (i + 1) + "\n");
                for (int k = 0; k < img1.getWidth(); k++)
                {
                    for (int l = 0; l < img1.getHeight(); l++)
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append("\"");
                        sb.append(cnt++);
                        sb.append("\"");
                        sb.append(" ");
                        sb.append(img1.getRGB(k, l));
                        sb.append("\n");
                        writer.write(sb.toString());
                    }
                }
                writer.flush();
                writer.close();
            }

//            image.getRGB(4,4);
//            System.out.println((image.getRGB(222,202)>>16)&255);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static Result3D merge3DimBlock(Result3D block1, Result3D block2)
    {
        Map<Tuple, Integer> map = new HashMap<>();

        for (int i = 0; i < block1.tuples.length; i++)
        {
            map.put(block1.tuples[i], block1.values[i]);
        }

        for (int i = 0; i < block2.tuples.length; i++)
        {
            if (map.containsKey(block2.tuples[i]))
            {
                map.put(block2.tuples[i], map.get(block2.tuples[i]) + 1);
            } else
            {
                map.put(block2.tuples[i], 1);
            }
        }
        Result3D result = new Result3D();
        result.tuples = new Tuple[map.entrySet().size()];
        result.values = new int[map.entrySet().size()];
        for (int i = 0; i < result.tuples.length; i++)
        {

        }
        return result;
    }

    /**
     * method creates map of combinations of color
     * current with his neighbours by axis (x and y)
     */
    public static Result3D map3Dim(int[][] key)
    {
        Result3D result = new Result3D();
        Map<Tuple, Integer> map = new HashMap<>();

        for (int i = 0; i < key.length; i++)
        {
            for (int j = 0; j < key[i].length; j++)
            {
                for (int m = 0; m < key.length; m++)
                {
                    for (int n = 0; n < key[m].length; n++)
                    {
                        Tuple t = new Tuple(key[i][j], key[i][m], key[j][n]);
                        System.out.println(t.z + ":" + t.x + ":" + t.y);
                        if (map.containsKey(t))
                        {
                            //System.out.println("increasing old value");
                            map.put(t, map.get(t) + 1); // increasing tuple counter by 1
                        } else
                        {
                            //System.out.println("putting new value");
                            map.put(t, 1); // putting new value to map
                        }
                    }
                }
            }
        }

        result.tuples = new Tuple[map.entrySet().size()];
        result.values = new int[map.entrySet().size()];
        int i = 0;
        for (Map.Entry<Tuple, Integer> entry : map.entrySet())
        {
            result.tuples[i] = entry.getKey();
            result.values[i++] = entry.getValue();
        }

        return result;
    }

    /**
     * method splits image into several chunks to avoid memory limitations caused by integer digit
     * name of image is inputed by 1 arg, result of method are length chunks, which are text files with
     * specified extensions
     * this method differs from cropAndHandleImage by writing single chunk as matrix
     *
     * @param img    name of image to split
     * @param dir    directory of output chunks to be stored
     * @param name   name of chunk
     * @param format extension on chunk
     * @param length number of chunks
     */
    public static void cropAndHandleImage3Dim(String img, String dir, String name, String format, int length)
    {
        try
        { // TODO implement iamge cropping without loading full image to memory
            int size = 0;
            BufferedImage image = ImageIO.read(new File(img));

            size = image.getHeight() / length - 1;

            for (int i = 0; i < length; i++)
            {
                BufferedImage img1 = image.getSubimage(0, i * size, image.getWidth(), size);
                long cnt = 0;
                File file = new File(getName(i + 1, dir, name, format, "#"));
                if (!file.exists())
                {
                    file.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                StringBuilder sb = new StringBuilder();
                for (int l = 0; l < img1.getHeight(); l++)
                {
                    writer.write("X" + (l + 1) + " ");
                }
                writer.write("\n");
                for (int k = 0; k < img1.getWidth(); k++)
                {
                    sb.append("\"");
                    sb.append(cnt++);
                    sb.append("\"");
                    sb.append(" ");
                    for (int l = 0; l < img1.getHeight(); l++)
                    {
                        sb.append(img1.getRGB(k, l));
                        sb.append(" ");
                    }
                    sb.append("\n");
                }
                writer.write(sb.toString());
                writer.flush();
                writer.close();
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * this method loads and image from file, crops it to several buffered iamges and return it
     * method don't writes to files
     *
     * @param bitmap name of picture
     * @param length number of images to be produced
     * @return splited images
     */
    public static BufferedImage[] loadImageAndCrop(String bitmap, int length) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(bitmap));
        int size = image.getHeight() / length-1;
        BufferedImage[] images = new BufferedImage[length];

        for (int i = 0; i < length-1; i++)
        {
            images[i] = image.getSubimage(0, i * size, image.getWidth(), size);
        }
	images[length-1]=image.getSubimage(0, image.getHeight()-size-1, image.getWidth(), size);
        return images;
    }

    public static Result mapBlock(int[] key)
    {
//        System.out.println("map start");
        Result result = new Result();
//        System.out.println("created output");
        HashMap<Integer, Integer> map = new HashMap<>();
//        System.out.println("created map");
        for (int i = 0; i < key.length; i++)
        {
            if (map.containsKey(key[i]))
            {
                map.put(key[i], map.get(key[i]) + 1);
            } else
            {
                map.put(key[i], 1);
            }
        }
//        System.out.println("mapping finished");
        int i = 0;
        result.key = new int[map.entrySet().size()];
        result.value = new int[map.entrySet().size()];
        for (Map.Entry<Integer, Integer> e : map.entrySet())
        {
            result.key[i] = e.getKey();
            result.value[i++] = e.getValue();
        }
        return result;
    }

    public static void build3DimHist(String bitmap, int length)
    {
        long startTime = System.currentTimeMillis();
        BufferedImage[] images = null;
        try
        {
            images = loadImageAndCrop(bitmap, length); // cropping images
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if (images == null)
        {
            return;
        }
        ExecutorService services = Executors.newCachedThreadPool();

//        length = 6;
        Result3D[] results = new Result3D[length];
        ImageMapper[] mappers = new ImageMapper[length];
        for (int i = 0; i < length; i++)
        {
            mappers[i] = new ImageMapper(images[i]);
            services.execute(mappers[i]);
        }
        services.shutdown();
        boolean finished = false;
        try
        {
            finished = services.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        if (!finished) return;
        else
        {
            for (int i = 0; i < length; i++)
            {
                results[i] = mappers[i].getResult();
            }
        }

/*        MapMerger merger = new MapMerger(results[0], results[1]);
        merger.merge();

        for (int i = 2; i < results.length; i++)
        {
            merger.setTarget(results[i]);
            merger.merge();
        }

        Result3D finalResult = merger.getResult();*/
	Result3D finalResult = results[0];
	int sum=0;
	for(int i = 0; i<finalResult.tuples.length; i++){
	    sum += finalResult.values[i];
	}
        System.out.println("finished");
        System.out.println("number of resulted tuples:" + finalResult.tuples.length);
	System.out.println("total pixel numer:" + sum);
	System.out.println("sqrt total pixel numer:" + Math.sqrt(sum));
        System.out.println("time elapsed (ms):" + (System.currentTimeMillis() - startTime));
	System.out.println("Testing");
        RMethodsTest test = new RMethodsTest("Test for 3Dim hist");
        test.test3DimHist(finalResult);
        test.pack();
        RefineryUtilities.centerFrameOnScreen(test);
        test.setVisible(true);

    }

    public void test()
    {
        System.out.println("test");
    }

    public void testWithParam(String param)
    {
        System.out.println("param=" + param);
    }
}
