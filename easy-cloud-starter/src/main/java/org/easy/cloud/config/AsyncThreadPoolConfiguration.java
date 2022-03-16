package org.easy.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.easy.cloud.exception.ServiceException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@EnableAsync
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class AsyncThreadPoolConfiguration implements AsyncConfigurer/*,DisposableBean*/ {

    @Value("${spring.task.execution.thread-name-prefix:my_task_executor_}")
    private String threadNamePrefix;
    @Value("${spring.task.execution.pool.core-pool-size:32}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-pool-size:1024}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.queue-capacity:64}")
    private int queueCapacity;
    @Value("${spring.task.execution.pool.keep-alive-seconds:60}")
    private int keepAliveSeconds;
    @Value("${spring.task.execution.pool.wait-for-tasks-to-complete-on-shutdown:false}")
    private boolean waitForTasksToCompleteOnShutdown;
    @Value("${spring.task.execution.pool.await-termination-seconds:1200}")
    private int awaitTerminationSeconds;

    @Override
    @Bean(name = "taskExecutor",initMethod = "initialize")
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //  线程名称前缀
        threadPool.setThreadNamePrefix(threadNamePrefix);
        //设置核心线程数
        threadPool.setCorePoolSize(corePoolSize);
        //设置最大线程数
        threadPool.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(queueCapacity);
        //线程空闲后的最大存活时间
        threadPool.setKeepAliveSeconds(keepAliveSeconds);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        //等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(awaitTerminationSeconds);

        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy(){
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            log.error("Task {} rejected from {},{} ",r.toString(),e);
            super.rejectedExecution(r,e);
            }
        });

        threadPool.setThreadFactory(new CustomizableThreadFactory(threadNamePrefix){
            @Override
            public Thread newThread(Runnable runnable) {
                Thread t= createThread(runnable);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.error("Task {} throw from {}",t.toString(),e);
                        throw new ServiceException(e.getMessage());
                     }
                });
                return t;
            }
        });

        //初始化线程
        threadPool.initialize();

        StringBuilder logB = new StringBuilder(128);
        List<Object>  logA = new ArrayList<>();

        logB.append("Initialized taskExecutor.");

        logB.append("threadNamePrefix:{}, ");
        logA.add(threadNamePrefix);


        logB.append("corePoolSize:{}, ");
        logA.add(corePoolSize);

        logB.append("maxPoolSize:{}, ");
        logA.add(maxPoolSize);

        logB.append("queueCapacity:{}, ");
        logA.add(queueCapacity);

        logB.append("keepAliveSeconds:{}, ");
        logA.add(keepAliveSeconds);

        logB.append("waitForTasksToCompleteOnShutdown:{}, ");
        logA.add(waitForTasksToCompleteOnShutdown);

        logB.append("awaitTerminationSeconds:{}\n\n");
        logA.add(awaitTerminationSeconds);


        log.info(logB.toString(), logA.toArray());




        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        //return new SimpleAsyncUncaughtExceptionHandler();
        return new AsyncExceptionHandler();
    }

//    @Override
//    public void destroy() throws Exception {
//        ((ThreadPoolTaskExecutor)getAsyncExecutor()).destroy();
//    }

    /**
     * 自定义异常处理类
     *
     * @author liunh
     */
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.error("SysAsyncExecutor Exception message - {}", throwable.getMessage());
            log.error("SysAsyncExecutor Exception Method name - {}", method.getName());
            for (Object param : obj) {
                log.error("SysAsyncExecutor Exception Parameter value - " + param);
            }
        }
    }
}
