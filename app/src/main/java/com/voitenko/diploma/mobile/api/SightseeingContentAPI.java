package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.SightseeingContent;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface SightseeingContentAPI {

    @GET("/api/sightseeing-contents/{id}")
    void getSightseeingContent(@Path("id") int id, Callback<SightseeingContent> response);

    @POST("/api/sightseeing-contents")
    void setSightseeingContent(@Body SightseeingContent sightseeingContent, Callback<String> response);

    @PUT("/api/sightseeing-contents")
    void editSightseeingContent(@Body SightseeingContent sightseeingContent, Callback<String> response);

    @DELETE("/api/sightseeing-contents/{id}")
    void deleteSightseeingContent(@Path("id") int id, Callback<SightseeingContent> response);

    @GET("/api/sightseeing-contents")
    void getAll(Callback<ArrayList<SightseeingContent>> response);


}
