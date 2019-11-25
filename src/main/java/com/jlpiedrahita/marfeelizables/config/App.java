package com.jlpiedrahita.marfeelizables.config;

import com.jlpiedrahita.marfeelizables.service.MarfeelizableStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@ComponentScan(basePackages = {"com.jlpiedrahita.marfeelizables"})
@PropertySource(value = {"classpath:application.properties"})
public class App {

    @Autowired
    private Environment env;

    @Bean(name = "httpClientServiceExecutor")
    public Executor httpClientServiceExecutor() {
        ThreadPoolTaskExecutor httpClientServiceExecutor = new ThreadPoolTaskExecutor();
        httpClientServiceExecutor.setCorePoolSize(20);
        httpClientServiceExecutor.setMaxPoolSize(50);
        httpClientServiceExecutor.setQueueCapacity(200);
        httpClientServiceExecutor.setThreadNamePrefix("http-client-service-");
        httpClientServiceExecutor.initialize();
        return httpClientServiceExecutor;
    }

    @Bean
    public MarfeelizableStrategy marfeelizableStrategy() {
        return MarfeelizableStrategy.htmlHeadKeywordsStrategy(
                env.getProperty("com.jlpiedrahita.marfeelizables.keywords", String[].class)
        );
    }
}
