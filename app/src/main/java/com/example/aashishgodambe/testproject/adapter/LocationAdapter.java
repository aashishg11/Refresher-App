package com.example.aashishgodambe.testproject.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aashishgodambe.testproject.R;
import com.example.aashishgodambe.testproject.model.Location;

/**
 * Created by aashishgodambe on 1/15/19.
 */

public class LocationAdapter extends PagedListAdapter<Location,LocationAdapter.LocationViewHolder> {

    public LocationAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_location_row, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = getItem(position);
        if (location != null) {
            holder.bindTo(location);
        }
    }

    private static DiffUtil.ItemCallback<Location> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Location>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Location oldLocation, Location newLocation) {
                    return oldLocation.getId() == newLocation.getId();
                }

                @Override
                public boolean areContentsTheSame(Location oldLocation,
                                                  Location newLocation) {
                    return oldLocation.equals(newLocation);
                }
            };

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView cityTV,locationTV;

        public LocationViewHolder(View itemView) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.tvCity);
            locationTV = itemView.findViewById(R.id.tvLocation);
        }

        public void bindTo(Location location) {
            cityTV.setText(location.getCity());
            locationTV.setText(location.getDetails());

        }
    }
}
