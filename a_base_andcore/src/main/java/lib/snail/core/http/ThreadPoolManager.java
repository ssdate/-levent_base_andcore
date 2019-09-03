package lib.snail.core.http;


import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * http thread pool manager
 * 2019-3-27 levent
 */
public class ThreadPoolManager {
    private String TAG = "ThreadPoolManager";

    //安全线程队列
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    //添加线程
    public void putQueue(Runnable runnable){
        if(runnable != null){
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //队列线程池
    private ThreadPoolExecutor threadPoolExecutor ;
    private ThreadPoolManager(){
        if(threadPoolExecutor == null){
            /***
             * 核心线程池大小
             * 最大线程池
             * 线程池中超过核心线程池大小最多可存活的时间
             * 时间单位
             * 阻塞队列
             * 拒绝策略
             */
            threadPoolExecutor = new ThreadPoolExecutor(
                    10,
                    30,
                    2,
                    TimeUnit.MINUTES,
                    new ArrayBlockingQueue<Runnable>(10),
                    rejectedExecutionHandler
            );
            threadPoolExecutor.execute(runnable);
        }else{
            threadPoolExecutor.execute(runnable);
        }
    }

    //拒绝策略
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if(runnable != null){
                try {
                    queue .put(runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    //执行任务
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                Runnable runn = null  ;
                try {
                    /**
                     * 阻塞式函数
                     */
//                    Log.i(TAG,"等待队列     "+queue.size());
                    runn = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(runn != null){
                    threadPoolExecutor.execute(runn);
                }
//                Log.i(TAG,"线程池大小      "+threadPoolExecutor.getPoolSize());

            }
        }
    };

    //single
    private static ThreadPoolManager threadPoolManager ;
    public static ThreadPoolManager getInstance(){
        if(threadPoolManager == null ){
            threadPoolManager = new ThreadPoolManager();
        }
        return threadPoolManager ;
    }

}
