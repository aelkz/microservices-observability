package com.microservices.polarflow.api.service.async.medical;

import com.microservices.polarflow.api.configuration.medical.NutritionistIntegrationConfiguration;
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

@Service
@Transactional
public class NutritionistIntegrationService implements IntegrationService<SyncStatus,Activity> {

    private static Logger logger = LoggerFactory.getLogger(NutritionistIntegrationService.class);

    @Autowired
    private NutritionistIntegrationConfiguration nutritionistConfig;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<SyncStatus> sendAsyncEvent(Activity activity) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        logger.info("async call: nutritionist service.");

        // Retrieve the API Key from user's profile.
        headers.set(nutritionistConfig.getApiKey(),activity.getUser().getNutritionistApiKey());

        HttpEntity<Activity> request = new HttpEntity<Activity>(activity, headers);

        ResponseEntity<SyncStatus> result =
                restTemplate.exchange("http://"+nutritionistConfig.getHost()+":"+nutritionistConfig.getPort()+nutritionistConfig.getPath(),
                        HttpMethod.POST, request, SyncStatus.class);

        System.out.println(result.getBody().getSynced());

        return CompletableFuture.completedFuture(result.getBody());
    }

}
