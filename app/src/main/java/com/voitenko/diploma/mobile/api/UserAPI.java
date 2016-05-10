package com.voitenko.diploma.mobile.api;
import com.voitenko.diploma.mobile.model.User;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserAPI {

    @GET("/api/users/id/{id}")
    public void getUser(@Path("id") int id, Callback<User> response);

    @GET("/api/users/email")
    public void getUserByEmail(@Query("email") String email, Callback<User> response);

    @GET("/api/users/login/{login}")
    public void getUserByLogin(@Path("login") String login, Callback<User> response);

    @GET("/api/users/validate/{login}")
    public void getPasswordByLogin(@Path("login") String login, Callback<User> response);

    @GET("/api/users")
    public void getAll(Callback<ArrayList<User>> response);

}