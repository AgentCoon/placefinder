package com.agentcoon.placefinder.mapquest.nominatim.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface NominatimClient {

    @GET("search")
    Call<List<NominatimResponseDto>> findLocations(@Query("key") String appKey,
                                                   @Query("country") String country,
                                                   @Query("city") String city,
                                                   @Query("format") String format);
}
