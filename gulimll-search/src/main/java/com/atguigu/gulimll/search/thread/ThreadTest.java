package com.atguigu.gulimll.search.thread;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.concurrent.*;

/**
 * @author BaiYu
 * @version 1.0
 * @date 2020/12/13 15:26
 */
public class ThreadTest {

    /*创建线程池*/
    public static ExecutorService service = Executors.newFixedThreadPool(10);

    /**
     * 异步任务
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("start");

//        /*异步任务runAsync*/
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            System.out.println("异步任务:" + Thread.currentThread().getId());
//            int i = 10 / 2;
//            System.out.println("异步任务:" + i);
//        }, service);
        /*-----------------------------------------------------------------------------------------------------*/
        /*异步任务supplyAsync*/
        /*方法完成后的感知*/
        /*
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务:" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("异步任务:" + i);
            return i;
        }, service).whenComplete((res, excption) -> {
            //虽然能得到异常信息,但无法修改返回数据
            System.out.println("异步任务成功完成,结果是:" + res + "异常是" + excption);
        }).exceptionally((throwable)->{
            //可以感知异常,同时返回默认值
            return 10;
        });
        */
        /*-----------------------------------------------------------------------------------------------------*/
        /*无论成功还是失败,方法执行完成后的处理*/
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务:" + Thread.currentThread().getId());
            int i = 10 / 0;
            System.out.println("异步任务:" + i);
            return i;
        }, service).handle((res, thr) -> {
            if (res != null) return res * 2;
            if (thr != null) return 0;
            return 0;
        });

        Integer integer = future.get();
        System.out.println(integer);
        System.out.println("end");

    }

    /**
     * 线程的main方法
     *
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void thread(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main....start....");
        /**
         * 继承Thread
         *      new Thread01().start();
         * 实现Runnable接口
         *      Runnable01 runnable01 = new Runnable01();
         *         new Thread(runnable01).start();
         * jdk1.5实现Callable接口 + FutureTask(可以拿到返回结果,可以处理异常)
         * 是一种阻塞等待,要等他执行完
         *      FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
         *      new Thread((futureTask)).start();
         *         //等待线程执行完成,获取返回结果
         *         Integer integer = futureTask.get();
         * 线程池
         *      给线程池直接提交任务
         *      public static ExecutorService service = Executors.newFixedThreadPool(10);
         *      service.execute(new Runnable01());
         *      service.execute(new Thread01());
         *      FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
         *      service.execute(futureTask);
         */

//        new Thread01().start();

//        Runnable01 runnable01 = new Runnable01();
//        new Thread(runnable01).start();

//        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//        new Thread((futureTask)).start();
//        Integer integer = futureTask.get();


//        service.execute(new Runnable01());
//        service.execute(new Thread01());
//        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
//        service.execute(futureTask);

        /**
         * 线程池的七大参数:
         * int corePoolSize,    核心线程数:除非设置超时属性线程池创建好后就准备就绪的线程数量,等待接受异步任务去调用
         * int maximumPoolSize, 最大线程数量:池里最大线程数,控制资源并发
         * long keepAliveTime,  存活时间:如果当前正在运行的线程数量大于核心数量,释放空闲的线程性能
         * TimeUnit unit,       时间单位
         * BlockingQueue<Runnable> workQueue,   阻塞队列:如果任务有很多,就会将目前多的任务放在队列里,只要有线程空闲,就会队列里取出新的任务
         * ThreadFactory threadFactory,         线程的创建工厂
         * RejectedExecutionHandler handler     如果队列满了的处理方式,按照我们指定的拒绝策略,拒绝执行任务
         *
         * 工作顺序:
         *      准备好核心线程,等待接收任务
         *      如果核心线程数满了,任务会放到队列,空闲的线程就回去队列里拿任务
         *      如果队列满了,开启新的线程,但只能开到最大线程数
         *      线程满了:就用拒绝策略
         *      线程没满:有空闲在指定的时间后释放max - core线程
         *
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                5 //new了5个thread
                , 200    //控制线程并发
                , 10   //存活时间,会释放空闲线程,最大大小减去核心大小
                , TimeUnit.SECONDS  //时间单位
                , new LinkedBlockingDeque<>(100000)  //任务队列,默认是integer最大值,必须添加并发峰值
                , Executors.defaultThreadFactory()  //线程的创建工厂,例如名字
                , new ThreadPoolExecutor.AbortPolicy()  //队列满了后,拒绝执行的方式
        );

        System.out.println("main....end....");
    }

    /**
     * 继承Thread
     */
    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread01当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("Thread01运行结果:" + i);
        }
    }

    /**
     * 实现Runnable接口
     */
    public static class Runnable01 implements Runnable {

        @Override
        public void run() {
            System.out.println("Runnable01当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("Runnable01运行结果:" + i);
        }
    }

    /**
     * 实现Callable接口
     */
    public static class Callable01 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable01当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("Callable01运行结果:" + i);
            return i;
        }
    }


}
