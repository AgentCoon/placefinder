package com.agentoon.placefinder.facebook.client;

import com.agentoon.placefinder.facebook.client.api.FacebookPlaceDto;
import com.agentoon.placefinder.facebook.client.api.FacebookResponseDto;
import com.agentoon.placefinder.facebook.client.exception.FacebookClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class FacebookApiGateway {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FacebookApiClient facebookApiClient;

    private final String accessToken;

    @Inject
    public FacebookApiGateway(FacebookApiClient facebookApiClient, String accessToken) {
        this.facebookApiClient = facebookApiClient;
        this.accessToken = accessToken;
    }

    public List<FacebookPlaceDto> searchPlaces(String searchPhrase, Float latitude, Float longitude,
                                                            Integer distance) throws FacebookClientException {
        FacebookResponseDto a = send(facebookApiClient.searchPlaces(
                searchPhrase, "place", "location,name",
                String.format("%s,%s",latitude, longitude),
                distance.toString(),
                accessToken),
                "Failed to find places");

        return a.getFacebookPlaceDtos();
    }

    private <T> T send(Call<T> call, String errorMsg) throws FacebookClientException {

        try {
            Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                logger.error("Error response {} from Facebook API, {}", response, errorMsg);
                throw new FacebookClientException(errorMsg);
            }

            return response.body();

        } catch (IOException e) {
            logger.error("IOException, {}. {}", errorMsg, e);
            throw new FacebookClientException(errorMsg);
        }
    }
}
