package com.example.fuelqueueapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.request.FuelQueueRequest;
import com.example.fuelqueueapplication.api.response.FuelQueueResponse;
import com.example.fuelqueueapplication.api.response.FuelStationDetailsResponse;
import com.example.fuelqueueapplication.util.Constants;
import com.example.fuelqueueapplication.util.DateTimeOperations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * fuel station detail activity class
 * **/
public class FuelStationDetailActivity extends AppCompatActivity {
    String id,location, userId;
    SharedPreferences sharedPreferences;

    // Define Elements
    TextView textViewFuelStationNameDetails;
    TextView textViewServiceStartAt;
    TextView textViewServiceEndAt;
    TextView textViewFuelType;
    TextView textViewVehicleCount;

    // API call interface
    FuelStationInterface fuelStationInterface;
    DateTimeOperations dateTimeOperations = new DateTimeOperations();

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        location = intent.getStringExtra("locationName");
        getSupportActionBar().setTitle("Enrolling to the Queue");

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        userId = sharedPreferences.getString(Constants.USER_ID,null);

        // Register elements
        textViewFuelStationNameDetails = findViewById(R.id.fuelStationNameDetails);
        textViewServiceStartAt = findViewById(R.id.startTimeDetails);
        textViewServiceEndAt = findViewById(R.id.endTimeDetails);
        textViewFuelType = findViewById(R.id.fuelTypeDetails);
        textViewVehicleCount = findViewById(R.id.noOfVehiclesInQueueDetails);

        // API Call
        fuelStationInterface =  ApiClient.getClient().create(FuelStationInterface.class);
        Call<FuelStationDetailsResponse> fuelStationDetails = fuelStationInterface.getFuelStationDetails(id);
        fuelStationDetails.enqueue(new Callback<FuelStationDetailsResponse>() {
            @Override
            public void onResponse(Call<FuelStationDetailsResponse> call, Response<FuelStationDetailsResponse> response) {
                if(response.isSuccessful()){
                    FuelStationDetailsResponse fuelStationDetailsResponse = response.body();

                    //Debug
                    System.out.println(fuelStationDetailsResponse.getEndingTime());
                    //Toast.makeText(FuelStationDetailActivity.this, "DEBUG: " + fuelStationDetailsResponse.getEndingTime(), Toast.LENGTH_SHORT).show();

                    // Bind details to the interface elements
                    textViewFuelStationNameDetails.setText(location);
                    textViewServiceStartAt.setText(fuelStationDetailsResponse.getStartingTime());
                    textViewServiceEndAt.setText(fuelStationDetailsResponse.getEndingTime());
                    textViewFuelType.setText(fuelStationDetailsResponse.getFuelType());
                    textViewVehicleCount.setText(String.valueOf(fuelStationDetailsResponse.getVehicleCount()));
                }
            }

            @Override
            public void onFailure(Call<FuelStationDetailsResponse> call, Throwable t) {
                Toast.makeText(FuelStationDetailActivity.this, "ERROR: CAN'T GET THE DETAILS!", Toast.LENGTH_SHORT).show();
                System.out.println("DEBUG LOG: " + t);
            }
        });

    }

    //on click for enroll to the queue
    public void onClickEnroll(View view) {
        String startTime = dateTimeOperations.getDate();
        FuelQueueRequest fuelQueueRequest = new FuelQueueRequest(id,"vehicleNumber",userId,"pumpId","status",startTime);
        Call<FuelQueueResponse> call =fuelStationInterface.createQueueRequest(fuelQueueRequest);

        call.enqueue(new Callback<FuelQueueResponse>() {
            @Override
            public void onResponse(Call<FuelQueueResponse> call, Response<FuelQueueResponse> response) {
                if(response.isSuccessful()){
                    FuelQueueResponse fuelQueueResponse = response.body();
                    Intent intent = new Intent(FuelStationDetailActivity.this, FuelStationDetailsRequestActivity.class);
                    intent.putExtra("queueId", fuelQueueResponse.getId());
                    intent.putExtra("id", id);
                    startActivity(intent);

                }else{
                    Toast.makeText(FuelStationDetailActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FuelQueueResponse> call, Throwable t) {
                Toast.makeText(FuelStationDetailActivity.this, "INTERNAL_SERVER_ERROR(CAN'T_REACH_SEVER)", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void exitOnClick(View view) {
        Intent intent = new Intent(FuelStationDetailActivity.this, FuelStationActivity.class);
        startActivity(intent);
        finish();
    }
}