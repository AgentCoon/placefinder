package com.agentcoon.placefinder.rest;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.placefinder.PlaceFinder;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlacefinderResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final PlaceFinder placeFinder;

    @Inject
    public PlacefinderResource(PlaceFinder placeFinder) {
        this.placeFinder = placeFinder;
    }

    @GET
    @Path("/{country}/{city}/{searchString}")
    public Response getRepository(@PathParam("country") String country, @PathParam("city")
            String city, @PathParam("searchString") String searchString) {

        logger.info("Request country: {}, city: {}, search str: {}", country, city, searchString);

        try{
            return Response.ok(placeFinder.findPlaces(country, city, searchString))
                    .build();
        } catch (GeoLocationException e) {
            return Response.status(HttpStatus.BAD_REQUEST_400).build();
        }
    }
}
