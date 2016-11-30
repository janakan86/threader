package com.jana.Framework;

/**
 * Created by janakan on 26/11/16.
 */
public interface TaskCallback {

    void onTaskCompleted(String threadName);
    void onException (Exception exception);
}
