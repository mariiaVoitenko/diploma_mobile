package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.Language;
import com.voitenko.diploma.mobile.model.Region;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface RegionAPI {

    @GET("/api/regions/{id}")
    void getRegion(@Path("id") int id, Callback<Region> response);

    @POST("/api/regions")
    void setRegion(@Body Region region, Callback<String> response);

    @DELETE("/api/regions/{id}")
    void deleteRegion(@Path("id") int id, Callback<Region> response);

    @GET("/api/regions")
    void getAll(Callback<ArrayList<Region>> response);

}
