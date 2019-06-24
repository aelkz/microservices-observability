package com.microservices.polarflow.api.controller;

import com.microservices.polarflow.api.controller.validator.EventValidator;
import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.service.ActivityService;
import com.microservices.polarflow.api.service.async.calendar.GoogleIntegrationService;
import com.microservices.polarflow.api.service.async.medical.CardiologistIntegrationService;
import com.microservices.polarflow.api.service.async.medical.NutritionistIntegrationService;
import com.microservices.polarflow.api.service.pojo.SyncStatus;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Validated //required for @Valid on method parameters such as @RequesParam, @PathVariable, @RequestHeader
public class SyncController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SyncController.class);

    @Autowired
    ActivityService service;

    @Autowired
    GoogleIntegrationService googleIntegrationService;

    @Autowired
    NutritionistIntegrationService nutritionistIntegrationService;

    @Autowired
    CardiologistIntegrationService cardiologistIntegrationService;

    @RequestMapping(path = "/v1/sync", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(
            value = "Sync polar user data across linked 3rd party software",
            notes = "The polar user data will be sent to all devices registered in the account user preferences.",
            response = Activity.class)
    public ResponseEntity<Activity> sync(@Valid @RequestBody Activity activity) {

        activity = service.save(activity);
        service.refresh(activity);

        CompletableFuture<SyncStatus> event1 = googleIntegrationService.sendAsyncEvent(activity);
        CompletableFuture<SyncStatus> event2 = nutritionistIntegrationService.sendAsyncEvent(activity);
        CompletableFuture<SyncStatus> event3 = cardiologistIntegrationService.sendAsyncEvent(activity);

        return ResponseEntity.ok().body(activity);
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new EventValidator());
    }
}
