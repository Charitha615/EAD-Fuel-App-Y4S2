package com.example.fuelqueueapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.request.FuelQueueRemoveRequest;
import com.example.fuelqueueapplication.api.response.FuelStationDetailsResponse;
import com.example.fuelqueueapplication.util.DateTimeOperations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * fuel station detail request activity class
 * **/
public class FuelStationDetailsRequestActivity extends AppCompatActivity {

    String id,queueId;
    DateTimeOperations dateTimeOperations = new DateTimeOperations();

    // Define Elements
    TextView textViewFuelStationNameDetailsRequest;
    TextView textViewServiceStartAtRequest;
    TextView textViewServiceEndAtRequest;
    TextView textViewFuelTypeRequest;
    TextView textViewVehicleCountRequest;
    TextView textViewUserWaitingTimeRequest;

    // API call interface
    FuelStationInterface fuelStationInterface;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_details_request);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        queueId = intent.getStringExtra("queueId");
        getSupportActionBar().setTitle("In the Queue for service");

        // Register elements
        textViewFuelStationNameDetailsRequest = findViewById(R.id.stationNameDetailsRequest);
        textViewServiceStartAtRequest = findViewById(R.id.startingTimeDetailsRequest);
        textViewServiceEndAtRequest = findViewById(R.id.endingTimeDetailsRequest);
        textViewFuelTypeRequest = findViewById(R.id.fuelTypeDetailsRequest);
        textViewVehicleCountRequest = findViewById(R.id.vehiclesInQueueDetailsRequest);
        textViewUserWaitingTimeRequest = findViewById(R.id.waitingTimeInTheQueueDetailsRequest);

        // API Call
        //TODO: Pass the ID from the the intent call
        fuelStationInterface =  ApiClient.getClient().create(FuelStationInterface.class);
        Call<FuelStationDetailsResponse> fuelStationDetails = fuelStationInterface.getFuelStationDetails(id);
        fuelStationDetails.enqueue(new Callback<FuelStationDetailsResponse>() {
            @Override
            public void onResponse(Call<FuelStationDetailsResponse> call, Response<FuelStationDetailsResponse> response) {
                if(response.isSuccessful()){
                    FuelStationDetailsResponse fuelStationDetailsResponse = response.body();

                    //Debug
                    System.out.println(fuelStationDetailsResponse.getEndingTime());
                   // Toast.makeText(FuelStationDetailsRequestActivity.this, "DEBUG: " + fuelStationDetailsResponse.getEndingTime(), Toast.LENGTH_SHORT).show();

                    // Bind details to the interface elements
                    textViewFuelStationNameDetailsRequest.setText(fuelStationDetailsResponse.getLocation());
                    textViewServiceStartAtRequest.setText(fuelStationDetailsResponse.getStartingTime());
                    textViewServiceEndAtRequest.setText(fuelStationDetailsResponse.getEndingTime());
                    textViewFuelTypeRequest.setText(fuelStationDetailsResponse.getFuelType());
                    int count = fuelStationDetailsResponse.getVehicleCount() -1;
                    textViewVehicleCountRequest.setText(String.valueOf(count));
                    int waitingTime = count*5;
                    textViewUserWaitingTimeRequest.setText(waitingTime+" minutes");
                }
            }

            @Override
            public void onFailure(Call<FuelStationDetailsResponse> call, Throwable t) {
                Toast.makeText(FuelStationDetailsRequestActivity.this, "ERROR: CAN'T GET THE DETAILS!", Toast.LENGTH_SHORT).show();
                System.out.println("DEBUG LOG: " + t);
            }
        });

    }

    //on click for move to fuel request form
    public void onClickPlaceRequest(View view) {
        Intent intent = new Intent(FuelStationDetailsRequestActivity.this, CreateFuelRequestPage.class);
        intent.putExtra("queueId", queueId);
        startActivity(intent);
    }

    public void onClickExit(View view) {
        String endTime = dateTimeOperations.getDate();
        FuelQueueRemoveRequest fuelQueueRemoveRequest = new FuelQueueRemoveRequest(endTime, "");
        Call<Void> call = fuelStationInterface.fuelQueueRemove("/api/FuelStation/removeFuelQueue/"+queueId,fuelQueueRemoveRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(FuelStationDetailsRequestActivity.this, FuelStationActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(FuelStationDetailsRequestActivity.this, "CHECK_THE_History_INPUTS", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FuelStationDetailsRequestActivity.this, "INTERNAL_SERVER_ERROR(CAN'T_REACH_SEVER)", Toast.LENGTH_SHORT).show();

            }
        });
    }
}