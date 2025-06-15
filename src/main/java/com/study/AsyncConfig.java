package com.study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);             // 기본 동시 실행 쓰레드 수
        executor.setMaxPoolSize(10);             // 최대 동시 실행 수
        executor.setQueueCapacity(100);          // 작업 큐 사이즈
        executor.setThreadNamePrefix("Async-");  // 디버깅용 prefix
        executor.initialize();
        return executor;
    }
    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor(); // 기본 @Async가 사용할 Executor 설정
    }
}
