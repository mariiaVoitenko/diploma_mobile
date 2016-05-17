package com.voitenko.diploma.mobile.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.BoringLayout;
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
import com.voitenko.diploma.mobile.R;
import com.voitenko.diploma.mobile.Utils;
import com.voitenko.diploma.mobile.model.Region;
import com.voitenko.diploma.mobile.model.Sightseeing;

import com.voitenko.diploma.mobile.task.RetrieveImageTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


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
        imageView.setImageBitmap(Utils.getBitmap(sightseeing.getId()));

        DisplayMetrics metrics = Utils.getMetrics(context);
        int width = metrics.widthPixels;
        int halfWidth = width / 2;
        int height = metrics.heightPixels;
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.item_details_content);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(halfWidth, height / 3, 1));
        imageView.setLayoutParams(new LinearLayout.LayoutParams(halfWidth, halfWidth, 1));
        return rowView;

    }

    public Sightseeing getItem(int position) {
        return objects.get(position);
    }
}