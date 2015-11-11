package lian.artyom;

import lian.artyom.mapper.ImageMapper;
import lian.artyom.mapper.ImageMapperFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static lian.artyom.RMethods.Result3D;

/**
 * Created by dodler on 05.11.15.
 */
public class HistogramBuilder {


    public BufferedImage image; // TODO refactor - remove picture reference to separate class with other

    private int threadSize;
    private String imgPath;

    public HistogramBuilder(int threadSize, String imgPath) {
        this.threadSize = threadSize;
        this.imgPath = imgPath;
    }

    public static final byte MAPPER_1_DIM = 0, MAPPER_2_DIM = 1, MAPPER_3_DIM = 2;
    private byte MAPPER_TYPE = MAPPER_1_DIM;

    public void setMapper(byte MAPPER_TYPE) {
        this.MAPPER_TYPE = MAPPER_TYPE;
    }

    public void build(byte MAPPER_TYPE) throws IOException {
        this.MAPPER_TYPE = MAPPER_TYPE;
        long startTime = System.currentTimeMillis();
        image = ImageIO.read(new File(imgPath));

        ExecutorService services = Executors.newCachedThreadPool();

        RMethods.Result3D[] results = new RMethods.Result3D[threadSize];
        ImageMapper[] mappers = new ImageMapper[threadSize];
        for (int i = 0; i < threadSize - 1; i++) {
            mappers[i] = ImageMapperFactory.getInstance().getMapper(MAPPER_TYPE, 0, (i + 1) * image.getHeight() / threadSize, image.getHeight() / threadSize, image);
            services.execute(mappers[i]);
        }

        mappers[threadSize - 1] = ImageMapperFactory.getInstance().getMapper(MAPPER_TYPE, 0, image.getHeight() - image.getHeight() / threadSize, image.getHeight() / threadSize, image);
        services.execute(mappers[threadSize - 1]);

        services.shutdown();
        boolean finished = false;
        try {
            finished = services.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("mapping succesfully finished.");


        if (!finished) return;
        else {
            for (int i = 0; i < threadSize; i++) {
                results[i] = mappers[i].getResult();
            }
        }

        MapMerger merger;
        Result3D finalResult;
        if (results.length > 1) {
            merger = new MapMerger(results[0], results[1]);
            merger.merge();

            for (int i = 2; i < results.length; i++) {
                merger.setTarget(results[i]);
                merger.merge();
            }
            finalResult = merger.getResult();
        }else{
            finalResult = results[0];
        }

        int sum = 0;
        for (int i = 0; i < finalResult.tuples.length; i++) {
            sum += finalResult.values[i];
        }
        System.out.println("finished");
        System.out.println("number of resulted tuples:" + finalResult.tuples.length);
        System.out.println("total pixel numer:" + sum);
        System.out.println("sqrt total pixel numer:" + Math.sqrt(sum));
        System.out.println("time elapsed (ms):" + (System.currentTimeMillis() - startTime));
        System.out.println("Testing");

        HistogramPlotter test = new HistogramPlotter("Histogram", finalResult);

        test.plot(MAPPER_TYPE, image.getWidth(), image.getHeight());
    }
}
