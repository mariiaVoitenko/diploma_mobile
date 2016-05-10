package com.voitenko.diploma.mobile.api;

import com.voitenko.diploma.mobile.model.Account;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface AccountAPI {

    @GET("/api/account/{id}")
    void getAccount(@Path("id") int id, Callback<Account> response);

    @POST("/api/account")
    Account setAccount(Callback<Account> response);

    @DELETE("/api/account/{id}")
    void deleteAccount(@Path("id") int id, Callback<Account> response);

    @GET("/api/prioritys")
    void getAll(Callback<ArrayList<Account>> response);

}
