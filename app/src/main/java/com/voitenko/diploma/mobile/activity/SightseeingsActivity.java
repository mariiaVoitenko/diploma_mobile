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
import com.voitenko.diploma.mobile.adapter.SightseeingListAdapter;
import com.voitenko.diploma.mobile.api.CountryAPI;
import com.voitenko.diploma.mobile.api.RegionAPI;
import com.voitenko.diploma.mobile.api.SightseeingAPI;
import com.voitenko.diploma.mobile.model.Region;
import com.voitenko.diploma.mobile.model.Sightseeing;
import com.voitenko.diploma.mobile.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SightseeingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sightseeings_layout);

        final int regionId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.REGION_ID));
        final SightseeingAPI sightseeingAPI = ServiceGenerator.createService(SightseeingAPI.class, ConstantsContainer.ENDPOINT);

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
                                TextView textView = (TextView) findViewById(R.id.chosenSightseeingID);
                                textView.setText(sightseeing.getId().toString());
                                Intent intent = new Intent(SightseeingsActivity.this, SightseeingDetailsActivity.class);
                                intent.putExtra(ConstantsContainer.SIGHTSEEING_ID, textView.getText().toString());
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
