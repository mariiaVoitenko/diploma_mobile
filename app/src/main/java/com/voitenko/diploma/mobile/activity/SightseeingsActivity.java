package com.voitenko.diploma.mobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.adapter.RegionListAdapter;
import com.voitenko.diploma.mobile.adapter.SightseeingListAdapter;
import com.voitenko.diploma.mobile.api.CategoryAPI;
import com.voitenko.diploma.mobile.api.CountryAPI;
import com.voitenko.diploma.mobile.api.RegionAPI;
import com.voitenko.diploma.mobile.api.SightseeingAPI;
import com.voitenko.diploma.mobile.model.Category;
import com.voitenko.diploma.mobile.model.Region;
import com.voitenko.diploma.mobile.model.Sightseeing;
import com.voitenko.diploma.mobile.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SightseeingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sightseeings_layout);
        final Spinner spinner = (Spinner) findViewById(R.id.category_spinner);

        final int regionId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.REGION_ID));
        final SightseeingAPI sightseeingAPI = ServiceGenerator.createService(SightseeingAPI.class, ConstantsContainer.ENDPOINT);
        final CategoryAPI categoryAPI = ServiceGenerator.createService(CategoryAPI.class, ConstantsContainer.ENDPOINT);

        categoryAPI.getAll(
                new Callback<ArrayList<Category>>() {
                    @Override
                    public void success(ArrayList<Category> result, Response response) {
                        String[] categories = new String[result.size() + 1];
                        categories[0] = getResources().getString(R.string.choose_the_category);
                        for (int i = 0; i < result.size(); i++) {
                            categories[i + 1] = result.get(i).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SightseeingsActivity.this,
                                android.R.layout.simple_spinner_item, categories);
                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                       int arg2, long arg3) {
                                final String selected = spinner.getSelectedItem().toString();
                                getSightSeeingsWithCategory(regionId, sightseeingAPI, selected);
                            }

                            public void onNothingSelected(android.widget.AdapterView<?> arg0) {
                                getSightSeeings(regionId, sightseeingAPI);
                            }


                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("!!!RETROFIT_ERROR!!!!!", error.getMessage());
                    }
                }

        );

        getSightSeeings(regionId, sightseeingAPI);
    }

    private void getSightSeeings(final int regionId, SightseeingAPI sightseeingAPI) {
        sightseeingAPI.getAll(
                new Callback<ArrayList<Sightseeing>>() {
                    @Override
                    public void success(ArrayList<Sightseeing> result, Response response) {
                        ListView regionsListView = (ListView) findViewById(R.id.sightseeings_list);

                        List<Sightseeing> thisRegionSightseeings = new ArrayList<>();
                        for (Sightseeing sightseeing : result) {
                            if (sightseeing.getRegion().getId() == regionId)
                                thisRegionSightseeings.add(sightseeing);
                        }

                        final ArrayAdapter<Sightseeing> adapter = new SightseeingListAdapter(SightseeingsActivity.this,
                                thisRegionSightseeings);
                        regionsListView.setAdapter(adapter);

                        regionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Sightseeing sightseeing = adapter.getItem(position);
                                Intent intent = new Intent(SightseeingsActivity.this, SightseeingDetailsActivity.class);
                                intent.putExtra(ConstantsContainer.SIGHTSEEING_ID, sightseeing.getId().toString());
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

    private void getSightSeeingsWithCategory(final int regionId, SightseeingAPI sightseeingAPI, final String categoryName) {
        sightseeingAPI.getAll(
                new Callback<ArrayList<Sightseeing>>() {
                    @Override
                    public void success(ArrayList<Sightseeing> result, Response response) {
                        ListView regionsListView = (ListView) findViewById(R.id.sightseeings_list);
                        Collections.sort(result);

                        List<Sightseeing> thisRegionSightseeings = new ArrayList<>();
                        if (!categoryName.equals(getResources().getString(R.string.choose_the_category))) {
                            for (Sightseeing sightseeing : result) {
                                if (sightseeing.getRegion().getId() == regionId && sightseeing.getCategory().getName().equals(categoryName))
                                    thisRegionSightseeings.add(sightseeing);
                            }
                        } else {
                            for (Sightseeing sightseeing : result) {
                                if (sightseeing.getRegion().getId() == regionId)
                                    thisRegionSightseeings.add(sightseeing);
                            }
                        }

                        final ArrayAdapter<Sightseeing> adapter = new SightseeingListAdapter(SightseeingsActivity.this,
                                thisRegionSightseeings);
                        regionsListView.setAdapter(adapter);

                        regionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Sightseeing sightseeing = adapter.getItem(position);
                                Intent intent = new Intent(SightseeingsActivity.this, SightseeingDetailsActivity.class);
                                intent.putExtra(ConstantsContainer.SIGHTSEEING_ID, sightseeing.getId().toString());
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
