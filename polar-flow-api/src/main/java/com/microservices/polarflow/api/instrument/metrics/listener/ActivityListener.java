package com.microservices.polarflow.api.instrument.metrics.listener;

import com.microservices.polarflow.api.model.Activity;
import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.persistence.PostPersist;

/**
 * NOTE: The JPA Listener works only with the model itself.
 * It is not possible to fetch children entities.
 */

@Lazy
@Component
public class ActivityListener {

    @Autowired
    MeterRegistry registry;

    @PostPersist
    public void handleAfterCreate(Activity activity) {
        // counter for all activities
        Metrics.counter("counter.activity", "type", "activity").increment();

        // counter for all running activities
        //final Counter counter2 = Counter.builder("").register();
        if (activity.isRunningActivity()) {
            Metrics.counter("counter.running", "type", "activity.running").increment();
        }

        // gauge for highest burned calories for week
        Gauge.builder("gauge.burned.calories", activity, Activity::getCalories).register(registry);

        // gauge for recovery status (training load) - always shows the last training load
        // registry.gauge("entity.status.count", Arrays.asList(Tag.of("type", "user"), Tag.of("status", "RECOVERY_STATUS")), userRepository, r -> r.count());
        Gauge.builder("gauge.training.load", () -> activity.getLoad()).register(registry);
    }
}
