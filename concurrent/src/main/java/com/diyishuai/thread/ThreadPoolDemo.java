package com.diyishuai.thread;

import com.diyishuai.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Bruce
 * @date: 2019-10-25
 * @description:
 */
public class ThreadPoolDemo {

    private static Map<String,Integer> map = new ConcurrentHashMap<String,Integer>();

    public static void main(String[] args) {
        System.out.println("JVM可用线程数:"+Runtime.getRuntime().availableProcessors());
        single();
        fix();
        cached();
        workStealing();
    }

    private static void single() {
        StringUtil.title("Single");
        do20(Executors.newSingleThreadExecutor());
    }
    private static void fix() {
        StringUtil.title("Fix 5");
        do20(Executors.newFixedThreadPool(5));
    }
    private static void cached() {
        StringUtil.title("Cached");
        do20(Executors.newCachedThreadPool());
    }

    private static void workStealing() {
        StringUtil.title("WorkStealing");
        do20(Executors.newWorkStealingPool());
    }

    private static void do20(ExecutorService executorService) {
        //模拟20个用户访问
        int n = 20;
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            AtomicInteger atomicInteger = new AtomicInteger();
            for (int i = 1; i <= n; i++) {
                final int tmp = i;
                executorService.execute(() -> {
                    String name = Thread.currentThread().getName();
                    Integer count = map.get(name);
                    map.put(name, count==null?1:count+1);
                    atomicInteger.incrementAndGet();
                    System.out.println(name+" -> " + tmp);
                });
            }
            while (atomicInteger.intValue() != n){

            }
            TimeUnit.SECONDS.sleep(1);
            System.out.println();
            System.out.println("线程数 :"+map.size());
            for (Map.Entry<String,Integer> entry : map.entrySet()) {
                System.out.println("线程 \t"+entry.getKey()+" 执行 >"+entry.getValue()+"< 次");
            }
            map.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
