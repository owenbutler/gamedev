package org.jgameengine.common;

import org.apache.commons.lang.math.RandomUtils;

public class ArrayUtils {

    static public void modArrayRandom2f(float[] arr, int mod) {

        arr[0] += RandomUtils.nextInt(mod * 2) - mod;
        arr[1] += RandomUtils.nextInt(mod * 2) - mod;
    }

}
