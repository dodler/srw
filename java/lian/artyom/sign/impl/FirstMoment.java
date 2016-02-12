package lian.artyom.sign.impl;

import lian.artyom.RMethods;
import lian.artyom.sign.Sign;

import java.util.List;

/**
 * Created by dodler on 26/12/15.
 */
public class FirstMoment implements Sign{
    int mx;
    @Override
    public void calc(RMethods.Result3D result) {
        Sign firstAngleMoment = new FirstAngleMoment();
        firstAngleMoment.calc(result);
        List pi = (List)firstAngleMoment.getResult();
        mx = 0;
        for(int i = 0; i<pi.size(); i++){
            mx += i*(int)pi.get(i);
        }
    }

    @Override
    public Object getResult() {
        return mx;
    }

    @Override
    public Class getResultType() {
        return Integer.class;
    }
}
