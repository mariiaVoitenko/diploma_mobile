package com.voitenko.diploma.mobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.Utils;
import com.voitenko.diploma.mobile.adapter.SightseeingListAdapter;
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

public class SightseeingDetailsActivity extends Activity {

    private int sightseeingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sightseeing_details);

        sightseeingId = Integer.parseInt(getIntent().getStringExtra(ConstantsContainer.SIGHTSEEING_ID));
        final SightseeingAPI sightseeingAPI = ServiceGenerator.createService(SightseeingAPI.class, ConstantsContainer.ENDPOINT);

        sightseeingAPI.getSightseeing(sightseeingId, new Callback<Sightseeing>() {
                    @Override
                    public void success(final Sightseeing result, Response response) {
                        ImageView imageView = (ImageView) findViewById(R.id.sightseeing_image);
                        imageView.setImageBitmap(Utils.getBitmap(sightseeingId));
                        TextView regionTextView = (TextView) findViewById(R.id.region);
                        regionTextView.setText(result.getRegion().getName());
                        TextView categoryTextView = (TextView) findViewById(R.id.category);
                        categoryTextView.setText(result.getCategory().getName());
                        TextView nameTextView = (TextView) findViewById(R.id.name);
                        nameTextView.setText(result.getName());
                        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                        ratingBar.setRating(result.getRating());
                        TextView infoTextView = (TextView) findViewById(R.id.info);
                        infoTextView.setText(result.getInfo());

                        ratingBar.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_UP) {
                                    int stars = getStars(event, ratingBar);
                                    Long votes = getVotesCount(result);
                                    final Float rating = getRating(stars, result);
                                    result.setRating(rating);
                                    result.setVotes_count(votes);
                                    sightseeingAPI.editSightseeing(result, new Callback<String>() {
                                        @Override
                                        public void success(String s, Response response) {
                                            Log.d("!!RETROFIT_SUCCESS!!!!", "RATING:" + rating);
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d("!!!RETROFIT_ERROR!!!!!", error.getMessage());
                                        }
                                    });
                                    ratingBar.setRating(rating.intValue());
                                    v.setPressed(false);
                                }
                                return true;
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

    private Float getRating(int stars, Sightseeing result) {
        Long votes = result.getVotes_count();
        if (votes == 0) votes++;
        return (result.getRating() * votes + stars) / (votes + 1);
    }

    @NonNull
    private Long getVotesCount(Sightseeing result) {
        return result.getVotes_count() + 1;
    }

    private int getStars(MotionEvent event, RatingBar ratingBar) {
        float touchPositionX = event.getX();
        float width = ratingBar.getWidth();
        float starsf = (touchPositionX / width) * 5.0f;
        return (int) starsf + 1;
    }


    public void viewOnMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(ConstantsContainer.SIGHTSEEING_ID, sightseeingId);
        startActivity(intent);
    }

    public void playAudio(View view) {
        try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            String path = Utils.getAudio(sightseeingId);
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e("Unable to play audio", e.toString());
        }
    }
}
