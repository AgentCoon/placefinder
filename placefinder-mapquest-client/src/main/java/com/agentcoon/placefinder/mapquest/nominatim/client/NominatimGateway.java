package com.agentcoon.placefinder.mapquest.nominatim.client;

import com.agentcoon.placefinder.mapquest.client.GeoLocationClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class NominatimGateway {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FORMAT = "json";

    private final NominatimClient nominatimClient;

    private final String appId;

    @Inject
    public NominatimGateway(NominatimClient nominatimClient, String appId) {
        this.nominatimClient = nominatimClient;
        this.appId = appId;
    }

    public List<NominatimResponseDto> findLocations(String country, String city) throws GeoLocationClientException {
        return send(nominatimClient.findLocations(appId, country, city, FORMAT),
                String.format("failed to find locations for country: %s city: %s", country, city));
    }

    private <T> T send(Call<T> call, String errorMsg) throws GeoLocationClientException {

        try {
            Response<T> response = call.execute();

            if (!response.isSuccessful()) {
                logger.error("Error response {} from Nominatim API, {}", response, errorMsg);
                throw new GeoLocationClientException(errorMsg);
            }

            return response.body();

        } catch (IOException e) {
            logger.error("IOException, {}. {}", errorMsg, e);
            throw new GeoLocationClientException(errorMsg);
        }
    }
}