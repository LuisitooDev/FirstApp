package com.example.firstapp;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class LocationAdapter implements ListAdapter {
    FragmentActivity activity;
    ArrayList<LocationSchema> locationArray;


    public LocationAdapter(FragmentActivity activity, ArrayList<LocationSchema> locationArray) {
        this.activity= activity;
        this.locationArray = locationArray;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return locationArray.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            view = layoutInflater.inflate(R.layout.item_location, null);

            TextView txtID = view.findViewById(R.id.item_id);
            TextView txtNombre = view.findViewById(R.id.item_name);
            TextView txtCordenadas = view.findViewById(R.id.item_latlng);
            Button btnMaps = view.findViewById(R.id.item_map);
            String latitud = String.valueOf(Double.parseDouble(String.valueOf(locationArray.get(i).getLatitude())));
            String longitud = String.valueOf(Double.parseDouble(String.valueOf(locationArray.get(i).getLongitude())));

            txtID.setText(locationArray.get(i).getLocation_id());
            txtNombre.setText(locationArray.get(i).getName());
            txtCordenadas.setText(latitud + "-" + longitud);

            btnMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + locationArray.get(i).getLatitude() + "," + locationArray.get(i).getLongitude());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    Context context = view.getContext();

                    context.startActivity(mapIntent);

                }
            });
        }
        return view;

    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return locationArray.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
