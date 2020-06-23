package com.microservices.fitness.api.service.async.medical;

import com.microservices.fitness.api.configuration.medical.CardiologistIntegrationConfiguration;
import com.microservices.fitness.api.instrument.tracer.ActivityTracer;
import com.microservices.fitness.api.model.Activity;
import com.microservices.fitness.api.service.async.IntegrationService;
import com.microservices.fitness.api.service.pojo.SyncStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class CardiologistIntegrationService implements IntegrationService<SyncStatus,Activity> {

    private static Logger logger = LoggerFactory.getLogger(CardiologistIntegrationService.class);

    @Autowired
    private CardiologistIntegrationConfiguration cardiologistConnfig;

    @Autowired
    private ActivityTracer tracer;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<SyncStatus> sendAsyncEvent(Activity activity) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // Retrieve the API Key from user's profile.
        headers.set(cardiologistConnfig.getApiKey(),activity.getUser().getCardiologistApiKey());

        String uri = "http://"+cardiologistConnfig.getHost()+":"+cardiologistConnfig.getPort()+cardiologistConnfig.getPath();
        logger.info("async call: cardiologist service at "+uri);

        // inject tracing data into the wire
        tracer.inject(uri, headers, HttpMethod.POST);

        HttpEntity<Activity> request = new HttpEntity<Activity>(activity, headers);

        ResponseEntity<SyncStatus> result =
                restTemplate.exchange(uri,
                        HttpMethod.POST, request, SyncStatus.class);

        System.out.println(result.getBody().getSynced());

        return CompletableFuture.completedFuture(result.getBody());
    }

}
