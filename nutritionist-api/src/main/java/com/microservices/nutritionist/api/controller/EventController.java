package com.microservices.nutritionist.api.controller;

import javax.validation.Valid;

import com.microservices.nutritionist.api.model.Event;
import com.microservices.nutritionist.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

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
    public ResponseEntity<Event> add(@RequestHeader HttpHeaders headers, @Valid @RequestBody Event e) {

        ResponseEntity response = null;

        if (service.checkApiKey(headers)) {
            e = service.save(e);
            response = ResponseEntity.ok().body(e);
        }else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("api-key header missing");
        }

        return response;
    }

}
