package com.agentcoon.placefinder.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collections;

import static com.agentcoon.placefinder.api.PlaceDto.PlaceBuilder.aPlace;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlacefinderResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    @Path("/{country}/{city}/{searchString}")
    public Response getRepository(@PathParam("country") String country, @PathParam("city")
            String city, @PathParam("searchString") String searchString) {

        logger.info("Request country: {}, city: {}, search str: {}", country, city, searchString);

//        return Response.ok(aPlace().withName("Fudge Philosophy")
//                .withLongitude(12.5f).withLatitude(25.8f).build())
//                .build();

        return Response.ok(Collections.singletonList(aPlace().withName("Fudge Philosophy")
                .withLongitude(12.5f).withLatitude(25.8f).build()))
                .build();
    }
}
