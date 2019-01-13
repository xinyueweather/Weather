package com.example.admin.xinyueapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.admin.xinyueapp.R;
import com.example.admin.xinyueapp.entity.Location;

public class LocationAdapter extends ArrayAdapter<Location> {
    public LocationAdapter(Activity context, ArrayList<Location> locations) {
        super(context , 0 , locations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_add_location, parent, false);
        }

        Location currentLocation = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.location_text);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        assert currentLocation != null;
        String name =currentLocation.getLocation();
        nameTextView.setText(name);

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.admin_text);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        String admin =currentLocation.getAdmin();
        numberTextView.setText(admin);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
        //return super.getView(position, convertView, parent);


    }
}
