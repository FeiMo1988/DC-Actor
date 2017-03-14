package dcactor.support.core;

/**
 * Created by chendingwei on 17/3/8.
 */

public class PreConditions {

    public static void checkNull(Object obj,String name,String detail) {
        if (obj == null) {
            throw new RuntimeException("Object ["+name+"] can't be null "+(detail == null? "":detail));
        }
    }
}
