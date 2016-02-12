package lian.artyom.sign.impl;

import lian.artyom.RMethods;
import lian.artyom.sign.Sign;

import java.util.*;

import static lian.artyom.RMethods.Result3D;

/**
 * Created by dodler on 26/12/15.
 */
public class FirstAngleMoment implements Sign {
    public int[] pk, mk;

    @Override
    public void calc(Result3D result) {
        int size = result.values.length;
        pk = new int[size];
        mk = new int[size];
        Collection zToSkip = new HashSet<Integer>();

        for (int k = 0; k<size; k++){
            int z = result.tuples[k].z, sum = 0, sum2 = 0;
            for(int m = 0; m<size; m++) {
                if (zToSkip.contains(m)){
                    continue;
                }
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (z == result.tuples[m].z){
                            zToSkip.add(m);
                            sum += result.values[m];
                            sum2 += (i*j*result.values[m]);
                        }
                    }
                }
            }
            pk[k] = sum;
            mk[k] = sum2;
        }
    }

    @Override
    public Object getResult() {
        return Arrays.asList(pk);
    }

    public List getVectorM(){
        return Arrays.asList(mk);
    }

    public List getVectorP(){
        return Arrays.asList(pk);
    }

    @Override
    public Class getResultType() {
        return List.class;
    }
}
