package com.example.fuelqueueapplication.recyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelqueueapplication.FuelStationDetailActivity;
import com.example.fuelqueueapplication.R;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;
import com.example.fuelqueueapplication.api.response.UserHistoryResponse;
import com.example.fuelqueueapplication.util.DateTimeOperations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
/**
 * user history adapter class
 * **/
public class UserHistoryRecyclerViewAdapter extends RecyclerView.Adapter<UserHistoryRecyclerViewAdapter.MyViewHolder> implements Filterable {
    List<UserHistoryResponse> historyResponseList;
    List<UserHistoryResponse> historyResponseFullList;
    Context context;
    DateTimeOperations dateTimeOperations =new DateTimeOperations();

    //constructor
    public UserHistoryRecyclerViewAdapter(Context context,List<UserHistoryResponse> historyResponseList) {
        this.context = context;
        this.historyResponseList = historyResponseList;
        this.historyResponseFullList = new ArrayList<>(historyResponseList);
    }

    //on create view holder
    @NonNull
    @Override
    public UserHistoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_history_raw_view, parent, false);
        return new UserHistoryRecyclerViewAdapter.MyViewHolder(view);
    }

    //on bind view holder
    @Override
    public void onBindViewHolder(@NonNull UserHistoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.location.setText(historyResponseList.get(position).getLocation());
        holder.entryTime.setText(historyResponseList.get(position).getStartDateTime());
        holder.leaveTime.setText(historyResponseList.get(position).getEndDateTime());
        holder.fuelAmount.setText(historyResponseList.get(position).getFuelAmount());
        String startTime = historyResponseList.get(position).getStartDateTime();
        String endTime = historyResponseList.get(position).getEndDateTime();
        try {
            String waitedTime = dateTimeOperations.getDateDifferance(startTime,endTime);
            holder.waitTime.setText(waitedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //get list count
    @Override
    public int getItemCount() {
        return historyResponseList.size();
    }

    //get the filter
    @Override
    public Filter getFilter() {
        return UserHistoryFilter;
    }

    //filter
    private Filter UserHistoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<UserHistoryResponse> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(historyResponseFullList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (UserHistoryResponse userHistoryResponse : historyResponseFullList) {
                    if (userHistoryResponse.getLocation().toLowerCase().contains(filterPattern)) {
                        filteredList.add(userHistoryResponse);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            historyResponseList.clear();
            historyResponseList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    //my view holder method
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView location, entryTime, leaveTime, waitTime, fuelAmount;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.userHistoryLocation);
            entryTime = itemView.findViewById(R.id.uHStartedTime);
            leaveTime = itemView.findViewById(R.id.uHEndTime);
            waitTime = itemView.findViewById(R.id.uHFuelAmount);
            fuelAmount = itemView.findViewById(R.id.uHWaitedTime);
            layout = itemView.findViewById(R.id.userHistoryRawViewOuterLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
