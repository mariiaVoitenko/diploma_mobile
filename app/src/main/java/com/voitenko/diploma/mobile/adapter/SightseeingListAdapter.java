package com.voitenko.diploma.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voitenko.diploma.mobile.ConstantsContainer;
import com.voitenko.diploma.mobile.model.Region;
import com.voitenko.diploma.mobile.model.Sightseeing;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import diploma.voitenko.com.diploma_mobile.R;

import static com.voitenko.diploma.mobile.ConstantsContainer.*;

public class SightseeingListAdapter extends ArrayAdapter<Sightseeing> {

    private final Context context;
    private final List<Sightseeing> objects;

    public SightseeingListAdapter(Context context, List<Sightseeing> objects) {
        super(context, R.layout.sightseeing_row, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.sightseeing_row, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView info = (TextView) rowView.findViewById(R.id.info);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.sightseeing_image);
        Sightseeing sightseeing = objects.get(position);
        name.setText(sightseeing.getName());
        info.setText(sightseeing.getInfo());
        try {
            String path = ENDPOINT + SIGHTSEEING_IMAGE_API + sightseeing.getId();
            Bitmap bitmap = new RetrieveImageTask().execute(path).get();
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        int width = getMetrics().widthPixels;
        int halfWidth = width / 2;
        int height = getMetrics().heightPixels;
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.item_details_content);
        Double v = width * 0.7;
        //linearLayout.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(halfWidth, height/3, 1));
        imageView.setLayoutParams(new LinearLayout.LayoutParams(halfWidth, halfWidth, 1));
        return rowView;

    }

    private DisplayMetrics getMetrics() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

    class RetrieveImageTask extends AsyncTask<String, Void, Bitmap> {

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

    public Sightseeing getItem(int position) {
        return objects.get(position);
    }
}