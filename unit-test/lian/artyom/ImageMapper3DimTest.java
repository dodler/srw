package lian.artyom;

import lian.artyom.mapper.ImageMapper;
import lian.artyom.mapper.ImageMapperFactory;
import lian.artyom.mapper.impl.ImageMapper1Dim;
import lian.artyom.mapper.impl.ImageMapper3Dim;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static lian.artyom.RMethods.*;

/**
 * Created by dodler on 07.11.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageMapper3DimTest
{

    private Result3D testResult;

    private int[][] imgSource;
    private int[][] imgSource4;

    private static final int size = 3;

    private Result3D testResult41Dim;

    private ImageMapper3Dim mapper;

    @Before
    public void before()
    {
        testResult = new Result3D(28);

        testResult.tuples[0] = new Tuple(1, 2, 3);
        testResult.values[0] = 2;
        testResult.tuples[1] = new Tuple(1, 2, 5);
        testResult.values[1] = 2;
        testResult.tuples[2] = new Tuple(1, 1, 3);
        testResult.values[2] = 2;
        testResult.tuples[3] = new Tuple(1, 1, 5);
        testResult.values[3] = 2;
        testResult.tuples[4] = new Tuple(3, 4, 1);
        testResult.values[4] = 2;
        testResult.tuples[5] = new Tuple(3, 4, 5);
        testResult.values[5] = 1;
        testResult.tuples[6] = new Tuple(3, 2, 1);
        testResult.values[6] = 1;
        testResult.tuples[7] = new Tuple(3, 2, 5);
        testResult.values[7] = 1;
        testResult.tuples[8] = new Tuple(4, 3, 5);
        testResult.values[8] = 1;
        testResult.tuples[9] = new Tuple(4, 2, 1);
        testResult.values[9] = 1;
        testResult.tuples[10] = new Tuple(4, 2, 5);
        testResult.values[10] = 1;
        testResult.tuples[11] = new Tuple(2, 1, 3);
        testResult.values[11] = 2;
        testResult.tuples[12] = new Tuple(2, 1, 5);
        testResult.values[12] = 2;
        testResult.tuples[13] = new Tuple(2, 4, 1);
        testResult.values[13] = 1;
        testResult.tuples[14] = new Tuple(2, 4, 5);
        testResult.values[14] = 1;
        testResult.tuples[15] = new Tuple(2, 3, 1);
        testResult.values[15] = 1;
        testResult.tuples[16] = new Tuple(2, 3, 5);
        testResult.values[16] = 1;
        testResult.tuples[17] = new Tuple(4, 5, 1);
        testResult.values[17] = 1;
        testResult.tuples[18] = new Tuple(4, 5, 3);
        testResult.values[18] = 1;
        testResult.tuples[19] = new Tuple(4, 3, 1);
        testResult.values[19] = 2;
        testResult.tuples[20] = new Tuple(4, 3, 3);
        testResult.values[20] = 1;
        testResult.tuples[21] = new Tuple(5, 4, 1);
        testResult.values[21] = 1;
        testResult.tuples[22] = new Tuple(5, 4, 3);
        testResult.values[22] = 1;
        testResult.tuples[23] = new Tuple(5, 3, 1);
        testResult.values[23] = 1;
        testResult.tuples[24] = new Tuple(5, 3, 3);
        testResult.values[24] = 1;
        testResult.tuples[25] = new Tuple(3, 5, 1);
        testResult.values[25] = 1;
        testResult.tuples[26] = new Tuple(3, 5, 3);
        testResult.values[26] = 1;
        testResult.tuples[27] = new Tuple(3, 4, 3);
        testResult.values[27] = 1;

        testResult41Dim = new Result3D(5);
        testResult41Dim.tuples[0] = new Tuple(1, 0, 0);
        testResult41Dim.values[0] = 2;
        testResult41Dim.tuples[1] = new Tuple(2, 0, 0);
        testResult41Dim.values[1] = 2;
        testResult41Dim.tuples[2] = new Tuple(3, 0, 0);
        testResult41Dim.values[2] = 2;
        testResult41Dim.tuples[3] = new Tuple(4, 0, 0);
        testResult41Dim.values[3] = 2;
        testResult41Dim.tuples[4] = new Tuple(5, 0, 0);
        testResult41Dim.values[4] = 1;

        imgSource = new int[size][size];
        imgSource[0][0] = 1;
        imgSource[0][1] = 2;
        imgSource[0][2] = 1;
        imgSource[1][0] = 3;
        imgSource[1][1] = 4;
        imgSource[1][2] = 2;
        imgSource[2][0] = 5;
        imgSource[2][1] = 4;
        imgSource[2][2] = 3;

        imgSource4 = new int[4][4];
        imgSource4[0][0] = 1;
        imgSource4[0][1] = 2;
        imgSource4[0][2] = 1;
        imgSource4[0][3] = 4;
        imgSource4[1][0] = 3;
        imgSource4[1][1] = 4;
        imgSource4[1][2] = 2;
        imgSource4[1][3] = 2;
        imgSource4[2][0] = 5;
        imgSource4[2][1] = 4;
        imgSource4[2][2] = 3;
        imgSource4[2][3] = 6;

        imgSource4[3][0] = 6;
        imgSource4[3][1] = 5;
        imgSource4[3][2] = 2;
        imgSource4[3][3] = 3;

        imgSourceRectangle = new int[3][4];
        imgSourceRectangle[0][0] = 1;
        imgSourceRectangle[0][1] = 2;
        imgSourceRectangle[0][2] = 1;
        imgSourceRectangle[0][3] = 4;
        imgSourceRectangle[1][0] = 3;
        imgSourceRectangle[1][1] = 4;
        imgSourceRectangle[1][2] = 2;
        imgSourceRectangle[1][3] = 2;
        imgSourceRectangle[2][0] = 5;
        imgSourceRectangle[2][1] = 4;
        imgSourceRectangle[2][2] = 3;
        imgSourceRectangle[2][3] = 6;

        mapper = new ImageMapper3Dim(0, 0, size, imgSource, size, size);
    }

    private int[][] imgSourceRectangle;

    @Test
    public void mapRectangleImage(){
        System.out.println("mapRectangleImage");
        ImageMapper mapper2 = new ImageMapper3Dim(0,0,4, imgSourceRectangle, 3,4);
        ImageMapper mapper1 = new ImageMapper1Dim(0,0,4,imgSourceRectangle,3,4);
        ExecutorService se = Executors.newCachedThreadPool();
        se.submit(mapper2);
        se.submit(mapper1);

        se.shutdown();
        try
        {
            se.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Result3D result = mapper2.getResult();
        Result3D result1 = mapper1.getResult();

        System.out.println(result.tuples.length);
        for(int i = 0; i<result.tuples.length; i++){
            System.out.println(result.tuples[i] + ":" + result.values[i]);
        }

        Result3D result2 = RMethodsUtils.sumResult3D(result, 3,4);
        System.out.println(result2.tuples.length);
        for(int i = 0; i<result2.tuples.length; i++){
            System.out.println(result2.tuples[i] + ":" + result2.values[i] + " -- " + result1.tuples[i] + ":" + result1.values[i]);
        }

        assert(true);
    }

    @Test
    public void map4Matrix(){
        System.out.println("map4Matrix");
        ImageMapper mapper1 = new ImageMapper3Dim(0,0, 4, imgSource4, 4,4);

        ExecutorService se = Executors.newCachedThreadPool();
        se.submit(mapper1);

        se.shutdown();
        try
        {
            se.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Result3D result = mapper1.getResult();

        System.out.println(result.tuples.length);
        for(int i = 0; i<result.tuples.length; i++){
            System.out.println(result.tuples[i] + ":" + result.values[i]);
        }

        Result3D result2 = RMethodsUtils.sumResult3D(result,4,4);
        System.out.println(result2.tuples.length);
        for(int i = 0; i<result2.tuples.length; i++){
            System.out.println(result2.tuples[i] + ":" + result2.values[i]);
        }

        assert(true);
    }

    @Test
    public void imageMapper3Dim_imageProcess_shouldReturnTestResultValues()
    {
        ExecutorService se = Executors.newCachedThreadPool();
        se.submit(mapper);

        se.shutdown();
        try
        {
            se.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Result3D result = mapper.getResult();

        assert (testResult.equals(result));
    }

    @Test
    public void imageMapper3Dim_3DimHistogram_shouldEquals1DimHistogram()
    {
        ExecutorService se = Executors.newCachedThreadPool();
        se.submit(mapper);

        se.shutdown();
        try
        {
            se.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        Result3D result = RMethodsUtils.sumResult3D(mapper.getResult(),3,3);

        assert (testResult41Dim.equals(result));
    }
}
