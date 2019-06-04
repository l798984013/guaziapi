package com.mianze.apiexport.component;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class ThreadAsyncConfigurer implements AsyncConfigurer {


    @Override
    @Bean
    public Executor getAsyncExecutor() {
        //定时任务配置为异步执行,如果是同步可能每分钟对应的任务不一定能执行到
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setThreadNamePrefix("MyAsync-");
        executor.initialize();
        return executor;
    }
}
