package lian.artyom.sign.impl;

import lian.artyom.RMethods;
import lian.artyom.sign.Sign;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by dodler on 26/12/15.
 */
public class SecondAngleMoment implements Sign {
    int g2;

    @Override
    public void calc(RMethods.Result3D result) {
        int size = result.values.length;
        int g2 = 0;
        for (int i = 0; i<size; i++){
            g2 += Math.pow(result.values[i],2);
        }
    }

    @Override
    public Object getResult() {
        return g2;
    }

    @Override
    public Class getResultType() {
        return Integer.class;
    }
}
