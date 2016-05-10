package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Category;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface CategoryAPI {

    @GET("/api/categories/{id}")
    void getCategory(@Path("id") int id, Callback<Category> response);

    @POST("/api/categories")
    void setCategory(@Body Category category, Callback<String> response);

    @DELETE("/api/categories/{id}")
    void deleteCategory(@Path("id") int id, Callback<Category> response);

    @GET("/api/categories")
    void getAll(Callback<ArrayList<Category>> response);
}

