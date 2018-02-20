package com.agentcoon.placefinder.infrastructure.facebook;

import facebook4j.*;

import javax.inject.Inject;

public class FacebookFacade {

    private final Facebook facebook;

    @Inject
    public FacebookFacade(Facebook facebook) {
        this.facebook = facebook;
    }

    public ResponseList<Place> searchPlaces(Float latitude, Float longitude, Integer distance, String searchString, String[] fields) throws FacebookException {

       GeoLocation center = new GeoLocation(latitude, longitude);
       Reading reading = new Reading().fields(fields);

       ResponseList<Place> places = facebook.searchPlaces(searchString, center, distance, reading);

       appendPlacesFromNextPages(places);

       return places;
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
