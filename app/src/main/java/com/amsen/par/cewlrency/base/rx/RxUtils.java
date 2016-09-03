package com.amsen.par.cewlrency.base.rx;

import java.util.Collections;
import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class RxUtils {
    public static <T extends Comparable> List<T> sort(List<T> list) {
        Collections.sort(list);
        return list;
    }
}
