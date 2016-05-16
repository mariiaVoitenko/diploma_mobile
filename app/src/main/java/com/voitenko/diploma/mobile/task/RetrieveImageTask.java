package com.voitenko.diploma.mobile.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {

    protected Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            return BitmapFactory.decodeStream((InputStream) url.getContent());
        } catch (Exception e) {
            Log.e("Image downloading error", "Async task failed");
        }
        return null;
    }
}