package com.example.fuelqueueapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.response.FuelRequestResponse;
import com.example.fuelqueueapplication.api.response.QueueResponse;
import com.example.fuelqueueapplication.recyclerViewAdapters.FuelQueueListViewAdapter;
import com.example.fuelqueueapplication.recyclerViewAdapters.FuelRequestsListViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * fuel station request activity class
 * **/
public class FuelStationRequestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FuelStationInterface fuelStationInterface;
    FuelRequestsListViewAdapter recyclerViewAdapter;

    //no create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_request_list);
        getSupportActionBar().setTitle("Request List");

        recyclerView = findViewById(R.id.FuelStationOwnerRequestListRecycleView);

        fuelStationInterface =  ApiClient.getClient().create(FuelStationInterface.class);
        Call<List<FuelRequestResponse>> listCall = fuelStationInterface.getFuelRequest();

        listCall.enqueue(new Callback<List<FuelRequestResponse>>() {
            @Override
            public void onResponse(Call<List<FuelRequestResponse>> call, Response<List<FuelRequestResponse>> response) {
                if (response.isSuccessful()) {
                    List<FuelRequestResponse> stationResponseList = response.body();
                    recyclerViewAdapter = new FuelRequestsListViewAdapter(FuelStationRequestListActivity.this, stationResponseList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }else {
                    Toast.makeText(FuelStationRequestListActivity.this, "ERROR: While Retrieving Fuel Requests", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FuelRequestResponse>> call, Throwable t) {
                Toast.makeText(FuelStationRequestListActivity.this, "ERROR: Internal Server Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}