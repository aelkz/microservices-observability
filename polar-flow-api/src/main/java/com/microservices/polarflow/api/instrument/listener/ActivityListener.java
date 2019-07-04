package com.microservices.polarflow.api.instrument.listener;

import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.model.RunningActivity;
import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.persistence.PostPersist;

@Lazy
@Component
public class ActivityListener {

    @Autowired
    MeterRegistry registry;

    @PostPersist
    public void handleAfterCreate(Activity activity) {
        // counter for all activities
        Counter counter1 = Metrics.counter("counter.activity", "type", "activity");
        counter1.increment();

        // counter for all running activities
        Counter counter2 = Metrics.counter("counter.running", "type", "activity.running");
        //final Counter counter2 = Counter.builder("").register();
        if (activity.isRunningActivity()) {
            counter2.increment();
        }

        // gauge highest burned calories for week
        Gauge burnedCaloriesGauge = Gauge
            .builder("gauge.burned.calories", activity, Activity::getCalories)
            .register(registry);

        // gauge maximum running distance for week
        if (activity.isRunningActivity()) {
            RunningActivity r = activity.getRunning();
            Gauge runningDistanceGauge = Gauge
                .builder("gauge.running.distance", r, RunningActivity::getDistance)
                .register(registry);
        }

        // gauge recovery status (training load) - always shows the last training load
        // registry.gauge("entity.status.count", Arrays.asList(Tag.of("type", "user"), Tag.of("status", "RECOVERY_STATUS")), userRepository, r -> r.count());
        Gauge trainingLoadGauge = Gauge
            .builder("gauge.training.load", () -> activity.getLoad())
            .register(registry);
    }
}
