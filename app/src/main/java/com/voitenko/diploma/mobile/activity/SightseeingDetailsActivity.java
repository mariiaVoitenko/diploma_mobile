package com.voitenko.diploma.mobile.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import java.util.Timer;
import java.util.TimerTask;

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

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void playAudio(View view) {
        try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Loading...");
            dialog.setMessage("Please wait.");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

            long delayInMillis = 5000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, delayInMillis);

            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/1.mp3";
            //String path = absolutePath.substring(0, absolutePath.length()-4)+"test/1.mp3";
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra(ConstantsContainer.PATH, path);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("Unable to play audio", e.toString());
        }
    }
}
