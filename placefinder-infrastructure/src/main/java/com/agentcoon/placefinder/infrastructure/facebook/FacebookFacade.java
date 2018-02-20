package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.*;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class FacebookFacade {

    private final Facebook facebook;
    private final PlaceMapper mapper;

    @Inject
    public FacebookFacade(Facebook facebook, PlaceMapper mapper) {
        this.facebook = facebook;
        this.mapper = mapper;
    }

    public List<FacebookPlace> searchPlaces(Float latitude, Float longitude, Integer distance, String searchString, String[] fields) throws FacebookException {

       GeoLocation center = new GeoLocation(latitude, longitude);
       Reading reading = new Reading().fields(fields);

       ResponseList<Place> places = facebook.searchPlaces(searchString, center, distance, reading);

       appendPlacesFromNextPages(places);

       return places.stream().map(mapper::from).collect(toList());
    }

    private void appendPlacesFromNextPages(ResponseList<Place> places) throws FacebookException {
        Paging<Place> nextPage = places.getPaging();
        boolean hasNextPage = nextPage != null;

        while (hasNextPage) {
            ResponseList<Place> nextPagePlaces = facebook.fetchNext(nextPage);
            places.addAll(nextPagePlaces);

            nextPage = nextPagePlaces.getPaging();
            hasNextPage = nextPage != null;
        }
    }
}
