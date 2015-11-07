package lian.artyom.mapper;

import lian.artyom.HistogramBuilder;
import lian.artyom.mapper.impl.ImageMapper1Dim;
import lian.artyom.mapper.impl.ImageMapper2Dim;
import lian.artyom.mapper.impl.ImageMapper3Dim;

import java.awt.image.BufferedImage;

/**
 * Created by dodler on 05.11.15.
 */
public class ImageMapperFactory {

    private static ImageMapperFactory instance = new ImageMapperFactory();

    private ImageMapperFactory() {
    }

    ;

    public static ImageMapperFactory getInstance() {
        return instance;
    }

    public ImageMapper getMapper(byte MAPPER_TYPE, int startX, int startY, int size, BufferedImage image) {

        int width = image.getWidth(), height = image.getHeight();
        int[][] colors = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                colors[i][j] = image.getRGB(i, j);
            }
        }

        switch (MAPPER_TYPE) {
            case HistogramBuilder.MAPPER_1_DIM:
                return new ImageMapper1Dim(startX, startY, size, colors, width, height);
            case HistogramBuilder.MAPPER_2_DIM:
                return new ImageMapper2Dim(startX, startY, size, colors, width, height);
            case HistogramBuilder.MAPPER_3_DIM:
                return new ImageMapper3Dim(startX, startY, size, colors, width, height);
        }
        throw new RuntimeException("Other types of image mappers are not supported. ");
    }
}
