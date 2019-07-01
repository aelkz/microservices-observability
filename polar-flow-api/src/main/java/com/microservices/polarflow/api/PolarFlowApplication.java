package com.microservices.polarflow.api;

import com.microservices.polarflow.api.repository.impl.CustomRepositoryImpl;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.micrometer.core.instrument.config.MeterFilter;
import io.opentracing.Tracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class PolarFlowApplication {

    private String jaegerHost;
    private Integer jaegerPort;

    /**
     * This filter can be used to exclude specific metrics.
     * In the method below, we are hiding tomcat specific metrics.
     * @return
     */
    @Bean
    public MeterFilter excludeTomcatFilter() {
        return MeterFilter.denyNameStartsWith("tomcat");
    }

    @Bean
    public Tracer tracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
            .withType(ConstSampler.TYPE)
            .withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
            .withFlushInterval(1000)
            .withMaxQueueSize(10000)
            .withSender(
                io.jaegertracing.Configuration.SenderConfiguration.fromEnv()
                    .withAgentHost(jaegerHost)
                    .withAgentPort(jaegerPort)
            )
            .withLogSpans(true);

        Configuration config = new Configuration("polar-flow-api-svc")
            .withSampler(samplerConfig)
            .withReporter(reporterConfig);

        return config.getTracer();
    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(PolarFlowApplication.class, args);
    }

}
