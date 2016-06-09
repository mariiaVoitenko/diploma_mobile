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
import com.voitenko.diploma.mobile.adapter.RegionListAdapter;
import com.voitenko.diploma.mobile.api.RegionAPI;
import com.voitenko.diploma.mobile.model.Region;
import com.voitenko.diploma.mobile.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regions_layout);

        final int countryId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.COUNTRY_ID));
        final RegionAPI regionAPI = ServiceGenerator.createService(RegionAPI.class, ConstantsContainer.ENDPOINT);

        regionAPI.getAll(
                new Callback<ArrayList<Region>>() {
                    @Override
                    public void success(ArrayList<Region> result, Response response) {
                        ListView regionsListView = (ListView) findViewById(R.id.regions_list);
                        Collections.sort(result);

                        List<Region> thisCountryRegions = new ArrayList<>();
                        for (Region region : result) {
                            if (region.getCountry().getId() == countryId)
                                thisCountryRegions.add(region);
                        }

                        final ArrayAdapter<Region> adapter = new RegionListAdapter(RegionsActivity.this,
                                thisCountryRegions);
                        regionsListView.setAdapter(adapter);

                        regionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Region region = adapter.getItem(position);
                                TextView textView = (TextView) findViewById(R.id.chosenRegionID);
                                textView.setText(region.getId().toString());
                                Intent intent = new Intent(RegionsActivity.this, SightseeingsActivity.class);
                                intent.putExtra(ConstantsContainer.REGION_ID, textView.getText().toString());
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
