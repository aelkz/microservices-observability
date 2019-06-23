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
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Validated //required for @Valid on method parameters such as @RequesParam, @PathVariable, @RequestHeader
public class EventController extends BaseController {

    @Autowired
    EventService service;

    @RequestMapping(path = "/v1/event", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(
            value = "Create new event",
            notes = "Creates new event. Returns created event with id.",
            response = Event.class)
    public ResponseEntity<Event> add(@Valid @RequestBody Event e) {
        e = service.save(e);
        return ResponseEntity.ok().body(e);
    }

    @InitBinder("event")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new EventValidator());
    }
}
