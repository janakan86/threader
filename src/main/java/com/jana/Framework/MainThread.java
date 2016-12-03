package com.jana.Framework;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class MainThread extends Thread implements TaskCallback {


    private ApplicationCallback applicationCallback;

    private ArrayList<Thread> listOfChildren = new ArrayList<>();
    private ArrayList<Callable<Object>> listOfTasks = new ArrayList<>();


    private List<Future<Object>> futures = new ArrayList<>();
    private ArrayList<Object> results = new ArrayList<>();

    private ThreadPoolFactory poolFactory = new FixedSizedThreadPoolFactory();


    private long noOfRemainingTasks = 0;

    public MainThread(ApplicationCallback applicationCallback){
        this.applicationCallback = applicationCallback;
    }

    @Override
    public void run() {
            ExecutorService executorService = poolFactory.getThreadPool(listOfTasks.size());

        try {
            noOfRemainingTasks = listOfTasks.size();
            futures =  executorService.invokeAll(listOfTasks);

            applicationCallback.onTasksComplete();

            executorService.shutdown();
        } catch (InterruptedException  /*| ExecutionException*/ e) {
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
        for(Future<Object> future : futures){
            results.add(future.get());
        }

        return results;
    }
}
