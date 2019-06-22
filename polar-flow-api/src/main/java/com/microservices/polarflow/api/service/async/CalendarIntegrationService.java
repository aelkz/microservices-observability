package com.microservices.polarflow.api.service.async;

import com.microservices.polarflow.api.configuration.CalendarIntegrationConfiguration;
import com.microservices.polarflow.api.model.Activity;
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

@Service
@Transactional
public class CalendarIntegrationService implements IntegrationService<SyncStatus,Activity> {

    private static Logger logger = LoggerFactory.getLogger(CalendarIntegrationService.class);

    @Autowired
    private CalendarIntegrationConfiguration calendarConfig;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<SyncStatus> sendEvent(Activity activity) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        logger.info("preparing to async call calendar integration service.");

        HttpEntity<Activity> request = new HttpEntity<Activity>(activity, headers);

        //SyncStatus result = restTemplate.postForObject(
        //    "http://"+calendarConfig.getHost()+":"+calendarConfig.getPort()+calendarConfig.getPath(), request, SyncStatus.class);

        ResponseEntity<SyncStatus> result =
                restTemplate.exchange("http://"+calendarConfig.getHost()+":"+calendarConfig.getPort()+calendarConfig.getPath(),
                HttpMethod.POST, request, SyncStatus.class);

        System.out.println(result.getBody().getSynced());

        return CompletableFuture.completedFuture(result.getBody());
    }

}
