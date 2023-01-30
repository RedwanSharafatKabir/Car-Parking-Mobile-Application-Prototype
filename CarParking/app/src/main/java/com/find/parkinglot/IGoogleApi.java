package com.find.parkinglot;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGoogleApi {
    @GET ("maps/api/dorections/json")
    Observable<String> getDirections(
            @Query("mode") String mode,
            @Query("transit_routing_preference") String transit_routing,
            @Query("origin") String from,
            @Query("transit_routing_preference") String to,
            @Query("transit_routing_preference") String key
    );
}
