package com.microservices.polarflow.api.service.async.calendar;

import com.microservices.polarflow.api.configuration.calendar.GoogleIntegrationConfiguration;
import com.microservices.polarflow.api.instrument.tracer.ActivityTracer;
import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.service.async.IntegrationService;
import com.microservices.polarflow.api.service.pojo.SyncStatus;
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

// https://www.baeldung.com/rest-template
// http://appsdeveloperblog.com/spring-resttemplate-tutorial/
// https://www.tutorialspoint.com/spring_boot/spring_boot_rest_template.htm
// https://java2blog.com/spring-restful-client-resttemplate-example/
// https://medium.com/red6-es/uploading-a-file-with-a-filename-with-spring-resttemplate-8ec5e7dc52ca
// https://stackoverflow.com/questions/34045321/http-post-using-json-in-spring-rest/34046931
// https://www.codepedia.org/ama/how-to-make-parallel-calls-in-java-with-completablefuture-example
// https://praveergupta.in/using-asynchrony-to-reduce-response-times-in-java-8-a10d254877fd

@Service
@Transactional
public class GoogleIntegrationService implements IntegrationService<SyncStatus,Activity> {

    private static Logger logger = LoggerFactory.getLogger(GoogleIntegrationService.class);

    @Autowired
    private GoogleIntegrationConfiguration googleConfig;

    @Autowired
    private ActivityTracer tracer;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<SyncStatus> sendAsyncEvent(Activity activity) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        // Retrieve the API Key from user's profile.
        headers.set(googleConfig.getApiKey(),activity.getUser().getGoogleCalendarApiKey());

        String uri = "http://"+ googleConfig.getHost()+":"+ googleConfig.getPort()+ googleConfig.getPath();
        logger.info("async call: google calendar service at "+uri);

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
