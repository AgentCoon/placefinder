package com.agentoon.placefinder.facebook.client;

import com.agentoon.placefinder.facebook.client.api.FacebookResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FacebookApiClient {

    @GET("search")
    Call<FacebookResponseDto> searchPlaces(@Query("q") String query,
                                           @Query("type") String type,
                                           @Query("fields") String fields,
                                           @Query("center") String center,
                                           @Query("distance") String distance,
                                           @Query("access_token") String accessToken);
}
