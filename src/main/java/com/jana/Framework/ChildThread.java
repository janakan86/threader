package com.jana.Framework;



public class ChildThread extends Thread{

    MainThread mainThread;


    ChildThread(Runnable runnable,MainThread mainThread)
    {

        super(runnable);
        this.mainThread = mainThread;
    }

    public void run()
    {
        super.run();
       // TODO mainThread.onChildThreadCompletion(Thread.currentThread().getName());
    }


}
