package lian.artyom.sign;

import lian.artyom.RMethods.*;

/**
 * Created by dodler on 26/12/15.
 */
public interface Sign {
    void calc(Result3D result);
    Object getResult();
    Class getResultType();
}
