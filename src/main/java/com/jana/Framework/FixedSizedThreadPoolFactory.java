package com.jana.Framework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by janakan on 26/11/16.
 */
public class FixedSizedThreadPoolFactory implements ThreadPoolFactory {


    public ExecutorService getThreadPool(int size) {
        return Executors.newFixedThreadPool(size);

    }
}
