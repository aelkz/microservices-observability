package com.microservices.polarflow.api.instrument.listener;

import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.repository.ActivityRepository;
import com.microservices.polarflow.api.repository.UserRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.persistence.PostPersist;
import java.util.Arrays;

@Lazy
@Component
public class ActivityListener {

    @Autowired
    private MeterRegistry registry;

    @Autowired
    private UserRepository userRepository;

    @PostPersist
    public void handleAfterCreate(Activity activity) {
        // count all activities
        final Counter counter1 = Metrics.counter("activity.count", "type", "activity");
        counter1.increment();

        // count all running activities
        final Counter counter2 = Metrics.counter("running.count", "type", "activity.running");
        if (activity.getRunning() != null) {
            if (activity.getRunning().getDistance().intValue() > 0) {
                counter2.increment();
            }
        }

        // gauge expected burned calories for week

        // gauge expected running distance for week

        // gauge recovery status (training load)
        // registry.gauge("entity.status.count", Arrays.asList(Tag.of("type", "user"), Tag.of("status", "RECOVERY_STATUS")), userRepository, r -> r.count());

    }
}
