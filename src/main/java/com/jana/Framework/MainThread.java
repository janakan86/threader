package com.jana.Framework;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class MainThread extends Thread implements TaskCallback {



    private ArrayList<Thread> listOfChildren = new ArrayList<>();
    private ArrayList<Callable<Object>> listOfTasks = new ArrayList<>();


    private List<Future<Object>> futures = new ArrayList<>();
    private ArrayList<Object> results = new ArrayList<>();

    private ThreadPoolFactory poolFactory = new FixedSizedThreadPoolFactory();


    @Override
    public void run() {
        System.out.println(listOfTasks.size());
            ExecutorService executorService = poolFactory.getThreadPool(listOfTasks.size());

        try {
            futures =  executorService.invokeAll(listOfTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public String addChildThread(String threadName, Runnable work)
    {
        ChildThread child = new ChildThread(work, this);
        if(threadName != null)
        {
            child.setName(threadName);
        }

        listOfChildren.add(child);
        return child.getName();
    }


    public void addChildThread(Thread thread){

        listOfChildren.add(thread);
    }

    public void addChildThreads(int number, Runnable commonWork){

    }

    public void addFutureTask(){

    }



    public void addTask(Runnable task, int noOfChildren){
        for(int i = 0 ; i< noOfChildren;i++){
            listOfTasks.add(new TaskWrapper(this,task));
        }

    }

    public void addTask(Callable task , int noOfChildren){
        listOfTasks.add(new TaskWrapper(this,task));
        System.out.println(listOfTasks.size());
    }


    /**
     * start work on the submitted threads / tasks
     * @return
     */
    public boolean startWork(){

        //TODO submit to thread Pool
        new MainThread().start();
        return true;
    }

    /**
     * start work on the submitted tasks
     * @return
     */
    public boolean startWork(ThreadPoolFactory poolFactory){
        poolFactory.getThreadPool(listOfChildren.size());
        return true;
    }


    /**
     * callback will be fired when a child thread finishes its task
     * @param threadName
     */
    @Override
    public void onTaskCompleted(String threadName) {

    }

    @Override
    public void onException(Exception exception) {

    }


    public ArrayList<Object> getResults() throws ExecutionException, InterruptedException {
        for(Future future : futures){
            results.add(future.get());
        }

        return results;
    }
}
