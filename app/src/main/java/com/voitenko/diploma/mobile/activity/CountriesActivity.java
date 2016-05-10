package com.voitenko.diploma.mobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.api.CountryAPI;
import com.voitenko.diploma.mobile.model.Country;
import com.voitenko.diploma.mobile.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import diploma.voitenko.com.diploma_mobile.R;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CountriesActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries_layout);

        final CountryAPI countryAPI = ServiceGenerator.createService(CountryAPI.class, ConstantsContainer.ENDPOINT);
        countryAPI.getAll(
                new Callback<ArrayList<Country>>() {
                    @Override
                    public void success(ArrayList<Country> result, Response response) {
                        List<String> countriesNames = new ArrayList<>();
                        Log.d("!!!RESTOFIT_NORM!!!!!", result.toString());
                        for (Country country : result) {
                            countriesNames.add(country.getName());
                            Log.d("!!!RESTOFIT_NORM!!!!!", country.getName());
                        }
                        ListView countriesList = (ListView) findViewById(R.id.countries_list);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CountriesActivity.this,
                                android.R.layout.simple_list_item_1,
                                countriesNames.toArray(new String[countriesNames.size()]));
                        countriesList.setAdapter(adapter);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("!!!RESTOFIT_ERROR!!!!!", error.getMessage());
                    }
                }
        );


    }
}