package com.example.fuelqueueapplication.recyclerViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fuelqueueapplication.FuelStationDetailActivity;
import com.example.fuelqueueapplication.FuelStationRequestListActivity;
import com.example.fuelqueueapplication.R;
import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.request.ApprovalStatusUpdateRequest;
import com.example.fuelqueueapplication.api.response.FuelRequestResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * fuel request adapter class
 * **/
public class FuelRequestsListViewAdapter extends RecyclerView.Adapter<FuelRequestsListViewAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<FuelRequestResponse> requestResponseList;
    List<FuelRequestResponse> requestResponseArrayList;

    // API Interface
    FuelStationInterface fuelStationInterface;

    //constructor
    public FuelRequestsListViewAdapter(Context context, List<FuelRequestResponse> queueResponseList){
        this.context = context;
        this.requestResponseList = queueResponseList;
        this.requestResponseArrayList = new ArrayList<>(queueResponseList);
    }

    //get the filter
    @Override
    public Filter getFilter() {
        return FuelStationQueueFilter;
    }

    //filter
    private Filter FuelStationQueueFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<FuelRequestResponse> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(requestResponseList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (FuelRequestResponse queueResponse : requestResponseArrayList) {
                    if (queueResponse.getApprovalStatus().toLowerCase().contains(filterPattern)) {
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
            requestResponseList.clear();
            requestResponseList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    //on create view holder
    @NonNull
    @Override
    public FuelRequestsListViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_raw_view, parent, false);
        return new FuelRequestsListViewAdapter.MyViewHolder(view);
    }

    //on bind view holder
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FuelRequestsListViewAdapter.MyViewHolder holder, int position) {
        holder.textViewRequestedUserName.setText("📩 Request " + (position + 1) );
        if(requestResponseList.get(position).getApprovalStatus().equalsIgnoreCase("approve")){
            holder.textViewDescription.setText("✅ " + requestResponseList.get(position).getApprovalStatus());
            holder.StatusUpdateButton.setEnabled(false);
            holder.StatusUpdateButton.setBackgroundColor(Color.WHITE);
        } else {
            holder.textViewDescription.setText("🔃  " + requestResponseList.get(position).getApprovalStatus());
        }

    }

    //get list count
    @Override
    public int getItemCount() {
        return requestResponseList.size();
    }

    public void refreshView() {
        notifyDataSetChanged();
    }

    //my view holder method
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRequestedUserName;
        TextView textViewDescription;
        ImageView StatusUpdateButton;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            fuelStationInterface =  ApiClient.getClient().create(FuelStationInterface.class);
            textViewRequestedUserName = itemView.findViewById(R.id.requestedUser);
            textViewDescription = itemView.findViewById(R.id.requestDescription);
            StatusUpdateButton = itemView.findViewById(R.id.StatusUpdateButtonRequestList);
            StatusUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Status update API call.
                    String id = requestResponseList.get(getAdapterPosition()).getId();
                    ApprovalStatusUpdateRequest approvalStatusUpdateRequest = new ApprovalStatusUpdateRequest();
                    approvalStatusUpdateRequest.setApprovalStatus("approve");
                    Call<Void> response = fuelStationInterface.updateApprovalStatus(id, approvalStatusUpdateRequest);
                    response.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(itemView.getContext(), "Approval Status Updated!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, FuelStationRequestListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            refreshView();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(itemView.getContext(), "Error Updating Approval Status!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    refreshView();
                }
            });

        }
    }
}
