package com.example.fuelqueueapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;
import com.example.fuelqueueapplication.api.response.UserHistoryResponse;
import com.example.fuelqueueapplication.recyclerViewAdapters.FuelStationListRecyclerViewAdapter;
import com.example.fuelqueueapplication.recyclerViewAdapters.UserHistoryRecyclerViewAdapter;
import com.example.fuelqueueapplication.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * user history activity class
 * **/
public class UserHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserHistoryRecyclerViewAdapter recyclerViewAdapter;
    FuelStationInterface fuelStationInterface;
    SharedPreferences sharedPreferences;

    //om create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        getSupportActionBar().setTitle("All History");
        recyclerView = findViewById(R.id.historyRecyclerView);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        String userId = sharedPreferences.getString(Constants.USER_ID,null);

        fuelStationInterface =  ApiClient.getClient().create(FuelStationInterface.class);
        Call<List<UserHistoryResponse>> listCall = fuelStationInterface.getUserHistory(userId);

        listCall.enqueue(new Callback<List<UserHistoryResponse>>() {
            @Override
            public void onResponse(Call<List<UserHistoryResponse>> call, Response<List<UserHistoryResponse>> response) {
                if(response.isSuccessful()){
                    List<UserHistoryResponse> responseList = response.body();
                    recyclerViewAdapter = new UserHistoryRecyclerViewAdapter(UserHistoryActivity.this,responseList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }else {
                    Toast.makeText(UserHistoryActivity.this, "CAN'T_GET_THE_FUEL_STATIONS", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<UserHistoryResponse>> call, Throwable t) {
                Toast.makeText(UserHistoryActivity.this, "INTERNAL_SERVER_ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //to search from the history list
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feul_station_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.locationSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                recyclerViewAdapter.getFilter().filter(text);
                return false;
            }
        });
        return true;
    }
}