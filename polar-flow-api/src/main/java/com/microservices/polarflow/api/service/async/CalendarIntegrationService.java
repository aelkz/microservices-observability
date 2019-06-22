package com.microservices.polarflow.api.service.async;

import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.service.pojo.SyncStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Future;

// https://www.baeldung.com/rest-template
@Transactional
@Service
public class CalendarIntegrationService implements IntegrationService<SyncStatus,Activity> {

    private static Logger logger = LoggerFactory.getLogger(CalendarIntegrationService.class);

    @Async
    public Future<SyncStatus> sendEvent(Activity a) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        logger.info("preparing to async call calendar integration service.");

        HttpEntity<?> request = new HttpEntity<>(a, headers);

        SyncStatus result = restTemplate.postForObject(
            "http://localhost:8080/getGreeting?name=", request, SyncStatus.class);



        return new AsyncResult<SyncStatus>(result);
    }

}
