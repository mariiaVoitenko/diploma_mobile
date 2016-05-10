package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.Code;
import com.voitenko.diploma.mobile.model.Country;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface CodeAPI {

    @GET("/api/codes/{id}")
    void getCode(@Path("id") int id, Callback<Code> response);

    @POST("/api/codes")
    void setCode(@Body Category category, Callback<String> response);

    @DELETE("/api/codes/{id}")
    void deleteCode(@Path("id") int id, Callback<Code> response);

    @GET("/api/codes")
    void getAll(Callback<ArrayList<Code>> response);

}
