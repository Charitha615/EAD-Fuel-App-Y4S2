package com.example.fuelqueueapplication.recyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelqueueapplication.FuelStationDetailActivity;
import com.example.fuelqueueapplication.R;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;
import com.example.fuelqueueapplication.util.DateTimeOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * fuel station adapter class
 * **/
public class FuelStationListRecyclerViewAdapter extends RecyclerView.Adapter<FuelStationListRecyclerViewAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<FuelStationResponse> stationResponseList;
    List<FuelStationResponse> stationResponseListFull;
    DateTimeOperations dateTimeOperations = new DateTimeOperations();
    String dateNow = dateTimeOperations.getDate();

    //constructor
    public FuelStationListRecyclerViewAdapter(Context context, List<FuelStationResponse> stationResponseList) {
        this.context = context;
        this.stationResponseList = stationResponseList;
        stationResponseListFull = new ArrayList<>(stationResponseList);
    }

    //on create view holder
    @NonNull
    @Override
    public FuelStationListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shed_list_raw_view, parent, false);
        return new MyViewHolder(view);
    }

    //on bind view holder
    @Override
    public void onBindViewHolder(@NonNull FuelStationListRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.location.setText("⛽ " + stationResponseList.get(position).getLocation());
        String startTime = stationResponseList.get(position).getStartingTime();
        String endTime = stationResponseList.get(position).getEndingTime();
        if(startTime.equals("")){
            holder.status.setText("Closed");
            holder.cardView.setCardBackgroundColor(Color.rgb(110,110,110));
        }else if(endTime.equals("")){
            holder.status.setText("Available");
            holder.cardView.setCardBackgroundColor(Color.rgb(0,214,10));
        }else{
            int nowHour = Integer.parseInt(dateNow.substring(11,13));
            int startHour = Integer.parseInt(startTime.substring(0,2));
            int endHour = Integer.parseInt(endTime.substring(0,2));

            if(startHour<=nowHour && endHour>nowHour){
                holder.status.setText("Available");
                holder.cardView.setCardBackgroundColor(Color.rgb(0,214,10));
            }else{
                holder.status.setText("Pending");
                holder.cardView.setCardBackgroundColor(Color.rgb(255,193,7));
            }

        }
    }

    //get list count
    @Override
    public int getItemCount() {
        return stationResponseList.size();
    }

    //get the filter
    @Override
    public Filter getFilter() {
        return FuelStationFilter;
    }

    //filter
    private Filter FuelStationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<FuelStationResponse> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(stationResponseListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (FuelStationResponse fuelStationResponse : stationResponseListFull) {
                    if (fuelStationResponse.getLocation().toLowerCase().contains(filterPattern)) {
                        filteredList.add(fuelStationResponse);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            stationResponseList.clear();
            stationResponseList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    //my view holder method
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView location, status;
        LinearLayout layout;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.fuelStationLocation);
            status = itemView.findViewById(R.id.fuelStationStatus);
            cardView = itemView.findViewById(R.id.fuelStationCardView);
            layout = itemView.findViewById(R.id.StationRawViewOuterLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String locationName = stationResponseList.get(getAdapterPosition()).getLocation();
                    String id = stationResponseList.get(getAdapterPosition()).getId();
                    Intent intent = new Intent(context, FuelStationDetailActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("locationName", locationName);
                    context.startActivity(intent);
                }
            });

        }
    }
}
