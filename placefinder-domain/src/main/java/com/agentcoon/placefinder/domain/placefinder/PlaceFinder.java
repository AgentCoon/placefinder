package com.agentcoon.placefinder.domain.placefinder;

import com.agentcoon.placefinder.domain.facebook.FacebookGateway;
import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationException;
import com.agentcoon.placefinder.domain.geolocation.GeoLocationService;
import com.agentcoon.placefinder.domain.geolocation.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PlaceFinder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GeoLocationService geoLocationService;
    private final FacebookGateway facebookGateway;

    @Inject
    public PlaceFinder(GeoLocationService geoLocationService, FacebookGateway facebookGateway) {
        this.geoLocationService = geoLocationService;
        this.facebookGateway = facebookGateway;
    }

    public List<FacebookPlace> findPlaces(String country, String city, String searchString) throws GeoLocationException {

        List<Location> locations = geoLocationService.getLocations(country, city);

        List<FacebookPlace> facebookPlaces = new ArrayList<>();

        locations.forEach(location -> {

          logger.info("Finding places in {}", location.getDisplayName());

            List<FacebookPlace> places = null;

            try {
                places = facebookGateway.search(location.getLatitude(),
                        location.getLongitude(), location.calculateRadius(), searchString);
            } catch (FacebookGatewayException e) {
                e.printStackTrace();
            }

            facebookPlaces.addAll(places);
        });

        return facebookPlaces.stream().distinct().collect(toList());
    }
}