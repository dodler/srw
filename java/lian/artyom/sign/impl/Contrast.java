package lian.artyom.sign.impl;

import lian.artyom.RMethods;
import lian.artyom.sign.Sign;

/**
 * Created by dodler on 26/12/15.
 */
public class Contrast implements Sign {

    int g2 = 0;

    @Override
    public void calc(RMethods.Result3D result) {
        g2 = 0;
        int size= result.values.length;
        for (int i = 0; i<size; i++){
            g2 += Math.pow(result.values[i],2);
        }
    }

    @Override
    public Object getResult() {
        return null;
    }

    @Override
    public Class getResultType() {
        return null;
    }
}
