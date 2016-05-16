package com.voitenko.diploma.mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.voitenko.diploma.mobile.task.RetrieveImageTask;

import java.util.concurrent.ExecutionException;

import static com.voitenko.diploma.mobile.ConstantsContainer.ENDPOINT;
import static com.voitenko.diploma.mobile.ConstantsContainer.SIGHTSEEING_IMAGE_API;

public class Utils {

    public static DisplayMetrics getMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

    public static Bitmap getBitmap(long id) {
        try {
            String path = ENDPOINT + SIGHTSEEING_IMAGE_API + id;
            return new RetrieveImageTask().execute(path).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
