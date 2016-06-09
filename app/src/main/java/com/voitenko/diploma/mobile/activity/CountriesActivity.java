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
import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.adapter.CountryListAdapter;
import com.voitenko.diploma.mobile.api.CountryAPI;
import com.voitenko.diploma.mobile.model.Country;
import com.voitenko.diploma.mobile.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;

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
                        ListView countriesListView = (ListView) findViewById(R.id.countries_list);
                        Collections.sort(result);
                        final ArrayAdapter<Country> adapter = new CountryListAdapter(CountriesActivity.this,
                                result);
                        countriesListView.setAdapter(adapter);

                        countriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Country country = adapter.getItem(position);
                                TextView textView = (TextView) findViewById(R.id.chosenCountryID);
                                textView.setText(country.getId().toString());
                                Intent intent = new Intent(CountriesActivity.this, RegionsActivity.class);
                                intent.putExtra(ConstantsContainer.COUNTRY_ID, textView.getText().toString());
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("!!!RETROFIT_ERROR!!!!!", error.getMessage());
                    }
                }
        );


    }
}