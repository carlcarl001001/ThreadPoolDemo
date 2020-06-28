package com.example.carl.threadpooldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btOnClick(View view) {
        //testCache();
        //testFix();
        //testSingle();
        testScheduled();
    }
    /**
     * cacheThreadPool
     * 特点：可缓存线程池，如果线程池长度超过处理需求，可灵活回收空闲线程，
     *      若无可回收，则新建线程。
     * **/
    private void testCache(){
        ExecutorService cacheThreadPool= Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){
            try {
                Thread.sleep(2000);//添加延时，使得线程空闲
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int index =i;
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    log(Thread.currentThread().getName()+" "+index);
                }
            });
        }
    }

    /**
     * fixThreadPool
     * 特点：创建一个定长的线程池，可控制线程最大的并发数，超出的线程会在队列中等待。
     * **/
    private void testFix(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i=0;i<10;i++){
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    log(Thread.currentThread().getName()+ " "+index);
                    try {
                        Thread.sleep(2000);//每3个线程后会有2s的停顿，因为最大并发线程数量是3
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * SingleThreadExecutor
     * 特点：创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
     *      保证所有任务按照指定顺序（FIFO,LIFO,优先级）执行
     * **/
    private void testSingle(){
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i=0;i<10;i++){
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    log(Thread.currentThread().getName()+ " "+index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * newScheduledThreadPool
     * 特点：创建一个定长线程池，支持定时及周期性任务执行。
     * **/
    private void testScheduled(){
        log("testScheduled");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                log("delay 3 seconds");
//            }
//        },3, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log("delay 2 seconds,and execute every 3 seconds");
            }
        },2,3,TimeUnit.SECONDS);



    }


    private void log(String str){
        Log.i("chen",str);
    }


}
