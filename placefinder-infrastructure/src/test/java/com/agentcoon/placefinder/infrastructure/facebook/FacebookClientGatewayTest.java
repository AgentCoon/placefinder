package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import facebook4j.FacebookException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FacebookClientGatewayTest {

    private FacebookFacade facebook;

    private FacebookApiGateway facebookApiGateway;

    @Before
    public void setUp() {
        facebook = mock(FacebookFacade.class);

        facebookApiGateway = new FacebookApiGateway(facebook);
    }

    @Test
    public void searchPlaces() throws FacebookGatewayException, FacebookException {
        Float latitude = 15.45f;
        Float longitude = -15.78f;
        Integer distance = 1255;
        String searchString = "coffee";
        String[] fields = {"location", "name"};

        FacebookPlace facebookPlace = aFacebookPlace().build();

        when(facebook.searchPlaces(latitude, longitude, distance, searchString, fields)).thenReturn(Collections.singletonList(facebookPlace));

        List<FacebookPlace> places = facebookApiGateway.searchPlaces(latitude, longitude, distance, searchString);
        assertEquals(1, places.size());
        verify(facebook).searchPlaces(latitude, longitude, distance, searchString, fields);
    }

    @Test
            (expected=FacebookGatewayException.class)
    public void searchPlacesWhenError() throws FacebookException, FacebookGatewayException {
        Float latitude = 15.45f;
        Float longitude = -15.78f;
        Integer distance = 1255;
        String searchString = "coffee";
        String[] fields = {"location", "name"};

        doThrow(FacebookException.class).when(facebook).searchPlaces(latitude, longitude, distance, searchString, fields);

        facebookApiGateway.searchPlaces(latitude, longitude, distance, searchString);
    }
}
