package com.agentcoon.placefinder.rest;

import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.placefinder.NotFoundException;
import com.agentcoon.placefinder.domain.placefinder.PlaceFinder;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.agentcoon.placefinder.api.ErrorDto.Builder.anError;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlacefinderResource {

    private final PlaceFinder placeFinder;
    private final PlaceDtoMapper mapper;

    @Inject
    public PlacefinderResource(PlaceFinder placeFinder, PlaceDtoMapper mapper) {
        this.placeFinder = placeFinder;
        this.mapper = mapper;
    }

    @GET
    @Path("/{country}/{city}/{searchString}")
    public Response findLocations(@PathParam("country") String country, @PathParam("city")
            String city, @PathParam("searchString") String searchString) {

        try {
            return Response.ok(mapper.from(placeFinder.findPlaces(country, city, searchString)))
                    .build();
        } catch (NotFoundException e) {
            return Response.status(HttpStatus.NOT_FOUND_404).entity(anError()
                    .withMessage(String.format("Requested location %s, %s was not found.", country, city)).build())
                    .build();
        } catch (GeoLocationException e) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).entity(anError()
                    .withMessage("There was an error processing your request.").build())
                    .build();
        }
    }
}
