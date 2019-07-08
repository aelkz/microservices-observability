package com.microservices.polarflow.api.instrument.metrics;

import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.model.RunningActivity;
import com.microservices.polarflow.api.model.User;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivityMetrics implements BaseMetrics<Activity> {

    @Autowired
    MeterRegistry registry;

    public ActivityMetrics() { }

    @Override
    public void loadMetrics(Activity activity) {
        if (activity != null) {
            // gauge for max heart rate
            Metrics.gauge("gauge.heart.max", activity, Activity::getHrMax);

            // gauge for avg heart rate
            Metrics.gauge("gauge.heart.avg", activity, Activity::getHrAvg);

            // counter for every type of exercise
            if (activity.getSport() != null) {
                Metrics.counter("counter.activity.sport", "value", activity.getSport().getName()).increment();
            }
            if (activity.getUser() != null) {
                User u = activity.getUser();

                // gauge for user VO2 max
                Metrics.gauge("gauge.vo2max", u, User::getVo2max);
            }
            if (activity.isRunningActivity()) {
                RunningActivity r = activity.getRunning();

                // gauge for maximum running distance realized (track athlete evolution)
                Gauge runningDistanceGauge =
                        Gauge.builder("gauge.running.distance", r, RunningActivity::getDistance).register(registry);

                // gauge for user best running cadence (track athlete evolution)
                Metrics.gauge("gauge.running.cadence", r, RunningActivity::getCadenceAvg);

                // gauge for user best running index (track athlete evolution)
                Metrics.gauge("gauge.running.index", r, RunningActivity::getRunningIndex);

            }

        }
    }
}
