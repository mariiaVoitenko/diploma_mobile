package com.voitenko.diploma.mobile.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.Utils;
import com.voitenko.diploma.mobile.api.SightseeingAPI;
import com.voitenko.diploma.mobile.model.Sightseeing;
import com.voitenko.diploma.mobile.service.ServiceGenerator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private int sightseeingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        sightseeingId = getIntent().getIntExtra(ConstantsContainer.SIGHTSEEING_ID, 0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap map = googleMap;
        final SightseeingAPI sightseeingAPI = ServiceGenerator.createService(SightseeingAPI.class, ConstantsContainer.ENDPOINT);
        sightseeingAPI.getSightseeing(sightseeingId, new Callback<Sightseeing>() {
                    @Override
                    public void success(final Sightseeing result, Response response) {
                        LatLng sydney = new LatLng(result.getLatitude(), result.getLongitude());
                        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("!!!RETROFIT_ERROR!!!!!", error.getMessage());
                    }
                }
        );

    }
}
