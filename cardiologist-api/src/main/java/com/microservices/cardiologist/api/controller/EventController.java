package com.microservices.cardiologist.api.controller;

import com.microservices.cardiologist.api.controller.validator.EventValidator;
import com.microservices.cardiologist.api.model.Event;
import com.microservices.cardiologist.api.service.EventService;
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
public class EventController extends BaseController {

    // public static final String HEADER_USER_ID = "userId";

    @Autowired
    EventService service;

    @RequestMapping(path = "/v1/event", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(
            value = "Create new or update existing event",
            notes = "Creates new or updates exisitng event. Returns created/updated event with id.",
            response = Event.class)
    public ResponseEntity<Event> add(@Valid @RequestBody Event e) {
        //  @Valid @Size(max = 255, min = 5, message = "user e-mail size 5-255") @RequestHeader(name = HEADER_USER_ID) String userId

        e = service.save(e);
        return ResponseEntity.ok().body(e);
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new EventValidator());
    }
}
