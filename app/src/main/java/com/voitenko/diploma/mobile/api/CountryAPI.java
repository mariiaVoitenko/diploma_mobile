package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.Country;
import com.voitenko.diploma.mobile.model.Language;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface CountryAPI {

    @GET("/api/countries/{id}")
    void getCountry(@Path("id") int id, Callback<Country> response);

    @POST("/api/countries")
    void setCountry(@Body Category category, Callback<String> response);

    @DELETE("/api/countries/{id}")
    void deleteCountry(@Path("id") int id, Callback<Country> response);

    @GET("/api/countries")
    void getAll(Callback<ArrayList<Country>> response);

}
