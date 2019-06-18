package com.microservices.strava.api.controller;

import com.microservices.strava.api.model.Event;
import com.microservices.strava.api.service.EventService;
import io.swagger.annotations.*;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/event")
@Api(value = "/event", description = "Save a new STRAVA event", tags = {"event"})
@SwaggerDefinition(
        info = @Info(
                title = "STRAVA Event",
                description = "Register a new STRAVA event",
                version = "1.0.0",
                contact = @Contact(
                        name = "raphael abreu",
                        email = "raphael.alex@gmail.com",
                        url = "github.com/aelkz"
                )
        ),
        host = "localhost",
        basePath = "/v1",
        schemes = {SwaggerDefinition.Scheme.HTTP}
)
public class EventController {

    @Inject
    EventService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Save STRAVA event",
            notes = "Event",
            response = Event.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New event received.") })
    public Response calculate(Event e) {
        e = service.save(e);
        return Response.status(201).entity(e).build();
    }

    private Response error(int code, String message) {
        return Response
                .status(code)
                .entity(Json.createObjectBuilder()
                        .add("error", message)
                        .add("code", code)
                        .build()
                )
                .build();
    }

}
