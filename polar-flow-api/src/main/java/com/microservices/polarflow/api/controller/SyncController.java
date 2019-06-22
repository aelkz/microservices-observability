package com.microservices.polarflow.api.controller;

import com.microservices.polarflow.api.controller.validator.EventValidator;
import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.service.ActivityService;
import com.microservices.polarflow.api.service.async.CalendarIntegrationService;
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
import java.util.concurrent.Future;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Validated //required for @Valid on method parameters such as @RequesParam, @PathVariable, @RequestHeader
public class SyncController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SyncController.class);

    @Autowired
    ActivityService service;

    @Autowired
    CalendarIntegrationService calendarIntegrationService;

    @RequestMapping(path = "/v1/sync", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(
            value = "Sync polar user data across linked 3rd party software",
            notes = "The polar user data will be sent to all devices registered in the account user preferences.",
            response = Activity.class)
    public ResponseEntity<Activity> sync(@Valid @RequestBody Activity a) {

        a = service.save(a);
        service.refresh(a);

        Future<SyncStatus> greet1 = calendarIntegrationService.sendEvent(a);

        return ResponseEntity.ok().body(a);
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new EventValidator());
    }
}
