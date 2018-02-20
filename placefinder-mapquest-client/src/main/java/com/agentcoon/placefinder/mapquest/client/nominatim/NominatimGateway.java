package com.agentcoon.placefinder.mapquest.client.nominatim;

import com.agentcoon.placefinder.mapquest.client.GeoLocationClient;
import com.agentcoon.placefinder.mapquest.client.GeoLocationResponseDto;
import com.agentcoon.placefinder.mapquest.client.exception.GeoLocationClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class NominatimGateway implements GeoLocationClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String FORMAT = "json";
    private static final String CITY_TYPE = "city";

    private final NominatimClient nominatimClient;

    private final String appId;

    @Inject
    public NominatimGateway(NominatimClient nominatimClient, String appId) {
        this.nominatimClient = nominatimClient;
        this.appId = appId;
    }

    @Override
    public List<GeoLocationResponseDto> findCities(String country, String city) throws GeoLocationClientException {
        List<GeoLocationResponseDto> locations = send(nominatimClient.findLocations(appId, country, city, FORMAT),
                String.format("failed to find locations for country: %s city: %s", country, city));

        return locations.stream()
                .filter(location -> location.getType().equals(CITY_TYPE))
                .collect(toList());
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
