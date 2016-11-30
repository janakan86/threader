package com.jana.SampleApplication;

import com.jana.Framework.MainThread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {


        int[] array1  = new int[]{1,6,9,8};
        int[] array2  = new int[]{1,6,9,7};
        int[] array3  = new int[]{1,6,9,7};
        int[] array4  = new int[]{1,6,9,10};
        int[] array5  = new int[]{1,6,9,12};


        testMainThread mainThread = new testMainThread();

        mainThread.addTask(new ThreadAdder(array1),1);
        mainThread.addTask(new ThreadAdder(array2),1);
        mainThread.addTask(new ThreadAdder(array3),1);
        mainThread.addTask(new ThreadAdder(array4),1);
        mainThread.addTask(new ThreadAdder(array5),1);

        mainThread.startWork();

        try {
            ArrayList<Object> results = mainThread.getResults();

            for(Object result: results){
                System.out.println((Integer)result);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class testMainThread extends MainThread{



}


class ThreadAdder implements Callable<Integer> {

    int[] arrayToAdd;


    ThreadAdder(int[] arrayToAdd){
        this.arrayToAdd = arrayToAdd;
    }


    @Override
    public Integer call() throws Exception {
        int total = 0;

        for(int i = 0 ; i < arrayToAdd.length ; i++){
            Thread.sleep(2000);
            System.out.println("Counting array "+ Thread.currentThread().getName());
            total+=arrayToAdd[i];
        }

        return total;
    }
}
