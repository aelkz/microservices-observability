package com.microservices.polarflow.api.instrument.listener;

import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.repository.UserRepository;
import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.persistence.PostPersist;

@Lazy
@Component
public class ActivityListener {

    @Autowired
    private MeterRegistry registry;

    @Autowired
    private UserRepository userRepository;

    @PostPersist
    public void handleAfterCreate(Activity activity) {
        // counter for all activities
        final Counter counter1 = Metrics.counter("counter.activity", "type", "activity");
        counter1.increment();

        // counter for all running activities
        final Counter counter2 = Metrics.counter("counter.running", "type", "activity.running");
        //final Counter counter2 = Counter.builder("").register();
        if (activity.isRunningActivity()) {
            counter2.increment();
        }

        // gauge highest burned calories for week
        final Gauge burnedCaloriesGauge = Gauge
            .builder("gauge.burned.calories", () -> activity.getCalories())
            .register(registry);

        // gauge maximum running distance for week
        if (activity.isRunningActivity()) {
            final Gauge runningDistanceGauge = Gauge
                .builder("gauge.running.distance", () -> activity.getRunning().getDistance())
                .register(registry);
        }

        // gauge recovery status (training load) - always shows the last training load
        // registry.gauge("entity.status.count", Arrays.asList(Tag.of("type", "user"), Tag.of("status", "RECOVERY_STATUS")), userRepository, r -> r.count());
        final Gauge trainingLoadGauge = Gauge
            .builder("gauge.training.load", () -> activity.getLoad())
            .register(registry);

        // counter for every type of exercise
        registry.counter("counter.activity.sport", "value", activity.getSport().getName()).increment();

    }
}
