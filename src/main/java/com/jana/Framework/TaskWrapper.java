package com.jana.Framework;

import java.util.concurrent.Callable;

/**
 * Created by janakan on 26/11/16.
 */
public class TaskWrapper implements Callable {

    Callable callable;
    TaskCallback callback;

    TaskWrapper(TaskCallback callback, Callable callable){
        this.callback = callback;
        this.callable = callable;
    }

    TaskWrapper(TaskCallback callback, Runnable runnable){
        this.callback = callback;

        /* wrap Runnables with Callable as the framework handles Callables only*/

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
        callable.call();
        callback.onTaskCompleted("Test");
        return null;
    }

    /**@Override
    public  call() throws Exception {
        callable.call();
        callback.onTaskCompleted("Test");
    }*/
}
