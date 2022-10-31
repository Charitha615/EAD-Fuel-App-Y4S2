package com.example.fuelqueueapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.LoginInterface;
import com.example.fuelqueueapplication.api.request.UserRegisterRequest;
import com.example.fuelqueueapplication.api.response.UserRegisterResponse;
import com.example.fuelqueueapplication.persistance.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * registration activity class
 * **/
public class RegistrationActivity extends AppCompatActivity {
    String email, password, username, vehicleType;
    TextInputLayout emailInputLayout, passwordInputLayout, usernameInputLayout, vehicleTypeLayout;
    TextView errorMessage,signInLink;
    EditText emailInput, passwordInput, usernameInput;
    AutoCompleteTextView vehicleTypeInput;
    ArrayAdapter<String> arrayAdapter;
    String[] items = {"Car","Van", "Bus","Motorcycle", "Three Wheel", "Lorry"};
    LoginInterface loginInterface;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        emailInput = findViewById(R.id.rEmailInput);
        passwordInput = findViewById(R.id.rPasswordInput);
        usernameInput = findViewById(R.id.rUsernameInput);
        vehicleTypeInput = findViewById(R.id.rVehicleTypeInput);
        emailInputLayout = findViewById(R.id.registerEmailInputLayout);
        passwordInputLayout = findViewById(R.id.registerPasswordInputLayout);
        usernameInputLayout = findViewById(R.id.registerUsernameInputLayout);
        vehicleTypeLayout = findViewById(R.id.registerVehicleTypeInputLayout);
        errorMessage = findViewById(R.id.registerErrorMessage);
        signInLink = findViewById(R.id.signInLink);

        arrayAdapter = new ArrayAdapter<String>(this,R.layout.vehicle_item_view,items);
        loginInterface = ApiClient.getClient().create(LoginInterface.class);

        vehicleTypeInput.setAdapter(arrayAdapter);
        vehicleTypeInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vehicleType = adapterView.getItemAtPosition(i).toString();
            }
        });
    }

    //on click for sing up button
    public void onClick(View view) throws JSONException {
        errorMessage.setText("");
        emailInputLayout.setErrorEnabled(false);
        passwordInputLayout.setErrorEnabled(false);
        vehicleTypeLayout.setErrorEnabled(false);
        usernameInputLayout.setErrorEnabled(false);

        if (registrationValidation()) {
            singUp();
        }

    }
    //to move login activity
    public void linkOnClick(View view){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);

    }


    //input validation
    private boolean registrationValidation() {
        email = emailInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();
        username = usernameInput.getText().toString().trim();
        emailInput.setText(email);
        passwordInput.setText(password);
        usernameInput.setText(username);

        if (username.isEmpty()) {
            usernameInput.setError("please fill this field");
            return false;
        } else if (email.isEmpty()) {
            emailInputLayout.setError("please fill this field");
            return false;

        } else if (password.isEmpty()) {
            passwordInputLayout.setError("please fill this field");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Enter a valid email address");
            return false;

        } else if (password.length() > 30) {
            passwordInputLayout.setError("password is too long");
            return false;

        } else if (vehicleType.isEmpty()) {
            vehicleTypeLayout.setError("please fill this field");
            return false;

        }else {
            return true;
        }


    }

    //to register to the system
    private void singUp() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(username,email,password,"user",vehicleType);
        Call<UserRegisterResponse> call = loginInterface.userRegister(userRegisterRequest);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    emailInputLayout.setError(" ");
                    passwordInputLayout.setError(" ");
                    vehicleTypeLayout.setError(" ");
                    usernameInput.setError(" ");
                    errorMessage.setText("please check the details again");
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "INTERNAL_SERVER_ERROR(CAN'T_REACH_SEVER)", Toast.LENGTH_SHORT).show();
            }
        });

    }
}