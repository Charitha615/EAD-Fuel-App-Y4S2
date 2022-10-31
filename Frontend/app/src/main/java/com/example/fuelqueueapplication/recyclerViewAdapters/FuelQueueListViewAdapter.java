package com.example.fuelqueueapplication.recyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelqueueapplication.R;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;
import com.example.fuelqueueapplication.api.response.QueueResponse;

import java.util.ArrayList;
import java.util.List;
/**
 * fuel queue adapter class
 * **/
public class FuelQueueListViewAdapter extends RecyclerView.Adapter<FuelQueueListViewAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<QueueResponse> queueResponseList;
    List<QueueResponse> queueResponseArrayList;

    //constructor
    public FuelQueueListViewAdapter(Context context, List<QueueResponse> queueResponseList){
        this.context = context;
        this.queueResponseList = queueResponseList;
        this.queueResponseArrayList = new ArrayList<>(queueResponseList);
    }

    //get the filter
    @Override
    public Filter getFilter() {
        return FuelStationQueueFilter;
    }

    // filter
    private Filter FuelStationQueueFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<QueueResponse> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(queueResponseList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (QueueResponse queueResponse : queueResponseArrayList) {
                    if (queueResponse.getStationId().toLowerCase().contains(filterPattern)) {
                        filteredList.add(queueResponse);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            queueResponseList.clear();
            queueResponseList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    //on create view holder
    @NonNull
    @Override
    public FuelQueueListViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_list_raw_view, parent, false);
        return new MyViewHolder(view);
    }

    //on bind view holder
    @Override
    public void onBindViewHolder(@NonNull FuelQueueListViewAdapter.MyViewHolder holder, int position) {
        holder.textViewUserName.setText("👤 " + queueResponseList.get(position).getUserId());
        holder.textViewWaitingTime.setText("🕑 " + queueResponseList.get(position).getStartingDateTime());
    }

    //get list count
    @Override
    public int getItemCount() {
       return queueResponseList.size();
    }

    //my view holder method
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        TextView textViewWaitingTime;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.queueUserName);
            textViewWaitingTime = itemView.findViewById(R.id.waitingTimeQueueUser);
        }
    }
}
