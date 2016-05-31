package com.voitenko.diploma.mobile.task;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.service.DataConverter;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RetrieveAudioTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            InputStream input = (InputStream) url.getContent();
            try {
                File file = new File(ConstantsContainer.DOWNLOADS, "1.mp3");
                if (!file.exists())
                    file.createNewFile();
                FileUtils.copyInputStreamToFile(input, file);
            } finally {
                input.close();
            }
        } catch (Exception e) {
            Log.e("Image downloading error", "Async task failed");
        }
        return ConstantsContainer.DOWNLOADS + "1.mp3";
    }
}