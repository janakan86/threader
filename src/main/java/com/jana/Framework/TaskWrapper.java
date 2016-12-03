package com.jana.Framework;

import java.util.concurrent.Callable;

/**
 * Created by janakan on 26/11/16.
 */


interface TaskCallback {

    void onTaskCompleted(String threadName);
    void onException (Exception exception);
}


public class TaskWrapper implements Callable {

    Callable callable;
    TaskCallback callback;

    TaskWrapper(TaskCallback callback, Callable callable){
        this.callback = callback;
        this.callable = callable;
    }

    TaskWrapper(TaskCallback callback, Runnable runnable){
        this.callback = callback;

        /* wrap Runnables with Callable as the framework handles Callables only */

        callable = new Callable() {
            @Override
            public Object call() throws Exception {
                runnable.run();
                return null;
            }
        };
    }

    @Override
    public Object call() throws Exception {
        Object returnValue = callable.call();
        callback.onTaskCompleted("Test");
        return returnValue;
    }


}
