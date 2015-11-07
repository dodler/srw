package lian.artyom;

import lian.artyom.mapper.impl.ImageMapper1Dim;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static lian.artyom.RMethods.Result3D;
import static lian.artyom.RMethods.Tuple;

/**
 * Created by dodler on 07.11.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageMapper1DimTest {

    private int[][] image;
    private final static int width = 3, height = 3;

    private ImageMapper1Dim mapper1Dim;

    RMethods.Result3D testResult;

    @Before
    public void before() {
        image = new int[width][height];
        image[0][0] = 1;
        image[1][0] = 2;
        image[2][0] = 1;

        image[0][1] = 3;
        image[1][1] = 4;
        image[2][1] = 2;

        image[0][2] = 5;
        image[1][2] = 4;
        image[2][2] = 3;

        mapper1Dim = new ImageMapper1Dim(0, 0, height, image, width, height);

        testResult = new Result3D(5);
        testResult.tuples[0] = new Tuple(1, 0, 0);
        testResult.tuples[1] = new Tuple(2, 0, 0);
        testResult.tuples[2] = new Tuple(3, 0, 0);
        testResult.tuples[3] = new Tuple(4, 0, 0);
        testResult.tuples[4] = new Tuple(5, 0, 0);

        testResult.values[0] = 2;
        testResult.values[1] = 2;
        testResult.values[2] = 2;
        testResult.values[3] = 2;
        testResult.values[4] = 1;
    }

    @Test
    public void imageMapper1Dim_imageMatrixProceed_shouldReturnTestResultValues() {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(mapper1Dim);

        es.shutdown();
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Result3D result = mapper1Dim.getResult();

        assert (testResult.equals(result));
    }
}
