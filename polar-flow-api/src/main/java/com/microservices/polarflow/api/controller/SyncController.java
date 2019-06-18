package com.microservices.polarflow.api.controller;

import com.microservices.polarflow.api.controller.validator.EventValidator;
import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.service.ActivityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Validated //required for @Valid on method parameters such as @RequesParam, @PathVariable, @RequestHeader
public class SyncController extends BaseController {

    @Autowired
    ActivityService service;

    @RequestMapping(path = "/v1/sync", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(
            value = "Sync polar user data across linked 3rd party software",
            notes = "The polar user data will be sent to all devices registered in the account user preferences.",
            response = Activity.class)
    public ResponseEntity<Activity> sync(@Valid @RequestBody Activity e) {

        e = service.save(e);
        return ResponseEntity.ok().body(e);
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new EventValidator());
    }
}
