package lian.artyom.mapper;

import static lian.artyom.RMethods.Result3D;

/**
 * Created by dodler on 05.11.15.
 */
public abstract class ImageMapper extends Thread {

    protected int[][] image;
    protected int height, width;
    protected Result3D result;

    protected abstract void mapReduce();

    public abstract Result3D getResult();

}
