package com.agentcoon.placefinder.mapquest.client.nominatim;

import com.agentcoon.placefinder.mapquest.client.GeoLocationResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface NominatimClient {

    @GET("search")
    Call<List<GeoLocationResponseDto>> findLocations(@Query("key") String appKey,
                                                     @Query("country") String country,
                                                     @Query("city") String city,
                                                     @Query("format") String format);
}
