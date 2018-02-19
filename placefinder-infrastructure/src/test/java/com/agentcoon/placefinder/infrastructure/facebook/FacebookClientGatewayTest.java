package com.agentcoon.placefinder.infrastructure.facebook;

import com.agentcoon.placefinder.domain.facebook.FacebookGatewayException;
import com.agentcoon.placefinder.domain.placefinder.FacebookPlace;
import com.agentoon.placefinder.facebook.client.FacebookApiGateway;
import com.agentoon.placefinder.facebook.client.api.FacebookPlaceDto;
import com.agentoon.placefinder.facebook.client.exception.FacebookClientException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.agentcoon.placefinder.domain.placefinder.FacebookPlace.Builder.aFacebookPlace;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FacebookClientGatewayTest {

    private PlaceMapper mapper;
    private FacebookApiGateway facebookApiGateway;

    private FacebookClientGateway facebookClientGateway;

    @Before
    public void setUp() {
        mapper = mock(PlaceMapper.class);
        facebookApiGateway = mock(FacebookApiGateway.class);

        facebookClientGateway = new FacebookClientGateway(facebookApiGateway, mapper);
    }

    @Test
    public void searchPlaces() throws FacebookGatewayException, FacebookClientException {
        Float latitude = 15.45f;
        Float longitude = -15.78f;
        Integer distance = 1255;
        String searchString = "coffee";

        FacebookPlaceDto facebookPlaceDto = new FacebookPlaceDto();
        FacebookPlace facebookPlace = aFacebookPlace().build();

        when(facebookApiGateway.searchPlaces(searchString, latitude, longitude, distance)).thenReturn(Collections.singletonList(facebookPlaceDto));
        when(mapper.from(facebookPlaceDto)).thenReturn(facebookPlace);

        List<FacebookPlace> places = facebookClientGateway.search(latitude, longitude, distance, searchString);
        assertEquals(1, places.size());
        verify(facebookApiGateway).searchPlaces(searchString, latitude, longitude, distance);
        verify(mapper).from(facebookPlaceDto);
    }
}
