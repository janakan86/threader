package com.jana.Framework;

import java.util.concurrent.ExecutorService;

/**
 * Created by janakan on 26/11/16.
 */
public interface ThreadPoolFactory {

    ExecutorService getThreadPool(int size);

}
