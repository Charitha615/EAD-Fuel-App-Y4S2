package com.example.fuelqueueapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.interfaces.LoginInterface;
import com.example.fuelqueueapplication.api.request.FuelQueueRemoveRequest;
import com.example.fuelqueueapplication.api.request.FuelRequestRequest;
import com.example.fuelqueueapplication.api.response.UserRegisterResponse;
import com.example.fuelqueueapplication.util.Constants;
import com.example.fuelqueueapplication.util.DateTimeOperations;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * create fuel request activity class
 * **/
public class CreateFuelRequestPage extends AppCompatActivity {
    EditText amountInput;
    AutoCompleteTextView pumpInput;
    String pump;
    float amount;
    String[] items = {"1", "2", "3"};
    TextInputLayout amountInputLayout, pumpInputLayout;
    ArrayAdapter<String> arrayAdapter;
    FuelStationInterface fuelStationInterface;
    SharedPreferences sharedPreferences;
    String userId, queueId;
    DateTimeOperations dateTimeOperations = new DateTimeOperations();


    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fuel_request_page);
        Intent intent = getIntent();
        queueId = intent.getStringExtra("queueId");
        System.out.println("queueId :"+queueId);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        userId = sharedPreferences.getString(Constants.USER_ID,null);

        amountInput = findViewById(R.id.amountInput);
        pumpInput = findViewById(R.id.pumpNoInput);
        amountInputLayout = findViewById(R.id.amountInputLayout);
        pumpInputLayout = findViewById(R.id.pumpNoInputLayout);

        fuelStationInterface = ApiClient.getClient().create(FuelStationInterface.class);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.vehicle_item_view, items);
        pumpInput.setAdapter(arrayAdapter);
        pumpInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pump = adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    //on click for button
    public void onClick(View view) throws JSONException {
        amountInputLayout.setErrorEnabled(false);
        pumpInputLayout.setErrorEnabled(false);

        if (inputValidation()) {
            amountRequest();

        }

    }



    //send the fuel request
    private void amountRequest() {
        FuelRequestRequest fuelRequestRequest = new FuelRequestRequest(userId,amount,pump,"pending");
        Call<FuelRequestRequest> call = fuelStationInterface.createRequest(fuelRequestRequest);
        call.enqueue(new Callback<FuelRequestRequest>() {
            @Override
            public void onResponse(Call<FuelRequestRequest> call, Response<FuelRequestRequest> response) {
                if(response.isSuccessful()){
                    addHistory();
                }else{
                    Toast.makeText(CreateFuelRequestPage.this, "CHECK_THE_INPUTS", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FuelRequestRequest> call, Throwable t) {
                Toast.makeText(CreateFuelRequestPage.this, "INTERNAL_SERVER_ERROR(CAN'T_REACH_SEVER)", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //add to the fuel history
    private void addHistory() {
        String fuelAmount = String.valueOf(amount);
        String endTime = dateTimeOperations.getDate();
        FuelQueueRemoveRequest fuelQueueRemoveRequest = new FuelQueueRemoveRequest(endTime, fuelAmount+"L");
        Call<Void> call = fuelStationInterface.fuelQueueRemove("/api/FuelStation/removeFuelQueue/"+queueId,fuelQueueRemoveRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(CreateFuelRequestPage.this, ThankYouActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(CreateFuelRequestPage.this, "CHECK_THE_history_INPUTS", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CreateFuelRequestPage.this, "INTERNAL_SERVER_ERROR(CAN'T_REACH_SEVER)", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //check input validation
    private boolean inputValidation() {
        amount = Float.parseFloat(amountInput.getText().toString().trim());
        String stringAmount = amountInput.getText().toString().trim();
        amountInput.setText(stringAmount);

        if (stringAmount.isEmpty()) {
            amountInputLayout.setError("please fill this field");
            return false;
        } else if (pump.isEmpty()) {
            pumpInputLayout.setError("please fill this field");
            return false;
        } else {
            return true;
        }
    }
}