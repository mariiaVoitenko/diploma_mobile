package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.Language;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface LanguageAPI {

    @GET("/api/languages/{id}")
    void getLanguage(@Path("id") int id, Callback<Language> response);

    @POST("/api/languages")
    void setLanguage(@Body Language language, Callback<String> response);

    @DELETE("/api/languages/{id}")
    void deleteLanguage(@Path("id") int id, Callback<Language> response);

    @GET("/api/languages")
    void getAll(Callback<ArrayList<Language>> response);

}
