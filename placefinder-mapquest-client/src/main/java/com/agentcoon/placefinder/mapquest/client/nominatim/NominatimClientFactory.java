package com.agentcoon.placefinder.mapquest.client.nominatim;

import com.agentcoon.placefinder.mapquest.client.MapQuestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class NominatimClientFactory {

    private final ObjectMapper objectMapper;

    public NominatimClientFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public NominatimClient create(MapQuestConfiguration config) {
        return getRetrofitInstance(config).create(NominatimClient.class);
    }

    private Retrofit getRetrofitInstance(MapQuestConfiguration config) {
        return new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(config.getUrl())
                .addConverterFactory(
                        JacksonConverterFactory.create(objectMapper))
                .build();
    }

    private OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
