package com.bitkrx.config.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bitkrx.config.logging.CmeCommonLogger;

/**
 * @author  (주)씨엠이소프트 최종근
 * 참조 URL http://dveamer.github.io/java/SpringAsync.html
 *
 */
@Configuration
@EnableAsync
public class TradeAsyncConfig {
	
	protected CmeCommonLogger log = new CmeCommonLogger(this.getClass());
    
	private static final int CORE_POOL_SIZE = 125;
	private static final int MAX_POOL_SIZE = 512;
	private static final int QUEUE_CAPACITY = 1024;
	
    @Bean(name = "tradeTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE); //Set the ThreadPoolExecutor's core pool size. Default is 1.
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE); //Set the ThreadPoolExecutor's maximum pool size. Default is Integer.MAX_VALUE.
        
        //Set the capacity for the ThreadPoolExecutor's BlockingQueue. Default is Integer.MAX_VALUE.
        //Any positive value will lead to a LinkedBlockingQueue instance; any other value will lead to a SynchronousQueue instance.
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix("trade-");
        taskExecutor.initialize();
        return new HandlingExecutor(taskExecutor); // HandlingExecutor로 wrapping 합니다.
    }

    public class HandlingExecutor implements AsyncTaskExecutor {
        private AsyncTaskExecutor executor;

        public HandlingExecutor(AsyncTaskExecutor executor) {
            this.executor = executor;
        }

        @Override
        public void execute(Runnable task) {
            executor.execute(task);
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            executor.execute(createWrappedRunnable(task), startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            return executor.submit(createWrappedRunnable(task));
        }

        @Override
        public <T> Future<T> submit(final Callable<T> task) {
            return executor.submit(createCallable(task));
        }

        private <T> Callable<T> createCallable(final Callable<T> task) {
            return new Callable<T>() {
                @Override
                public T call() throws Exception {
                    try {
                        return task.call();
                    } catch (Exception ex) {
                        handle(ex);
                        throw ex;
                    }
                }
            };
        }

        private Runnable createWrappedRunnable(final Runnable task) {
            return new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Exception ex) {
                        handle(ex);
                    }
                }
            };
        }

        private void handle(Exception ex) {        	
        	log.ViewErrorLog("Failed to execute task. :: " + ex);
        }

    }
}
