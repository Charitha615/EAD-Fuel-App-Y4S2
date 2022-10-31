package com.example.fuelqueueapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * thank you activity class**/
public class ThankYouActivity extends AppCompatActivity {

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    //to move fuel station activity
    public void onClick(View view) {
        Intent intent = new Intent(ThankYouActivity.this, FuelStationActivity.class);
        startActivity(intent);
        finish();
    }
}