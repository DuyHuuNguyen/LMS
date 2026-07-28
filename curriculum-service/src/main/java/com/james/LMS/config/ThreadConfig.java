package com.james.LMS.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class ThreadConfig {

  @Bean
  public ExecutorService newVirtualThreadPerTaskExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean("VirtualThreadPerTaskExecutor")
  public AsyncTaskExecutor applicationTaskExecutor() {
    return new TaskExecutorAdapter(this.newVirtualThreadPerTaskExecutor());
  }
}
