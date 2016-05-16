package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.Region;
import com.voitenko.diploma.mobile.model.Sightseeing;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface SightseeingAPI {

    @GET("/api/sightseeings/{id}")
    void getSightseeing(@Path("id") int id, Callback<Sightseeing> response);

    @POST("/api/sightseeings")
    void setSightseeing(@Body Sightseeing sightseeing, Callback<String> response);

    @PUT("/api/sightseeings")
    public void editSightseeing(@Body Sightseeing sightseeing, Callback<String> response);

    @DELETE("/api/sightseeings/{id}")
    void deleteSightseeing(@Path("id") int id, Callback<Sightseeing> response);

    @GET("/api/sightseeings")
    void getAll(Callback<ArrayList<Sightseeing>> response);

}
