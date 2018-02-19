package com.agentoon.placefinder.facebook.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class FacebookClientFactory {

    private final ObjectMapper objectMapper;

    public FacebookClientFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public FacebookApiClient create(FacebookConfiguration config) {
        return getRetrofitInstance(config).create(FacebookApiClient.class);
    }

    private Retrofit getRetrofitInstance(FacebookConfiguration config) {
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
