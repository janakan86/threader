package com.jana.SampleApplication;

import com.jana.Framework.ApplicationCallback;
import com.jana.Framework.MainThread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ArrayAdder implements ApplicationCallback {

       testMainThread mainThread = new testMainThread(this);

        public void createAndGetTasksDone(){

            int[] array1  = new int[1000000];
            int[] array2  = new int[1000000];
            int[] array3  = new int[1000000];
            int[] array4  = new int[1000000];
            int[] array5  = new int[1000000];


            for(int i= 0 ; i<1000000; i++){
                array1[i] = i;
                array2[i] = i;
                array3[i] = i;
                array4[i] = i;
                array5[i] = i;
            }


            mainThread.addTask(new ThreadAdder(array1),1);
            mainThread.addTask(new ThreadAdder(array2),1);
            mainThread.addTask(new ThreadAdder(array3),1);
            mainThread.addTask(new ThreadAdder(array4),1);
            mainThread.addTask(new ThreadAdder(array5),1);

            mainThread.start();

            /*  calculate without using Threader*/
            int total =0;
            for(int i=0; i<array1.length;i++){
                total+=array1[i];
            }
            for(int i=0; i<array2.length;i++){
                total+=array2[i];
            }
            for(int i=0; i<array3.length;i++){
                total+=array3[i];
            }
            for(int i=0; i<array4.length;i++){
                total+=array4[i];
            }
            for(int i=0; i<array5.length;i++){
                total+=array5[i];
            }

            System.out.println("Total of arrays using linear counting "+ total);


        }

    @Override
    public void onTasksComplete() {
        try {
            ArrayList<Object> results = mainThread.getResults();

            int total = 0;
            for(Object result: results){
                total += (Integer)result;
            }
            System.out.println("Total of arrays using Threader is "+ total);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new ArrayAdder().createAndGetTasksDone();
    }


}


class testMainThread extends MainThread{

    testMainThread(ApplicationCallback callback){
         super(callback);
    }

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
            total+=arrayToAdd[i];
        }

        return total;
    }
}
