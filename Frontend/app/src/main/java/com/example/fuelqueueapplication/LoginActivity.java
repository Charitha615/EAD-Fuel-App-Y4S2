package com.example.fuelqueueapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelqueueapplication.api.ApiClient;
import com.example.fuelqueueapplication.api.interfaces.FuelStationInterface;
import com.example.fuelqueueapplication.api.interfaces.LoginInterface;
import com.example.fuelqueueapplication.api.request.UserRegisterRequest;
import com.example.fuelqueueapplication.api.response.UserRegisterResponse;
import com.example.fuelqueueapplication.persistance.DBHelper;
import com.example.fuelqueueapplication.util.Constants;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * login activity class
 * **/
public class LoginActivity extends AppCompatActivity {
    String password, username;
    TextInputLayout passwordInputLayout, usernameInputLayout;
    TextView errorMessage;
    EditText passwordInput, usernameInput;
    LoginInterface loginInterface;
    SharedPreferences sharedPreferences;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,MODE_PRIVATE);

        passwordInputLayout = findViewById(R.id.loginPasswordInputLayout);
        usernameInputLayout = findViewById(R.id.loginUsernameInputLayout);
        passwordInput = findViewById(R.id.lPasswordInput);
        usernameInput = findViewById(R.id.lUsernameInput);
        errorMessage = findViewById(R.id.loginErrorMessage);

        loginInterface = ApiClient.getClient().create(LoginInterface.class);

    }

    //on click for login button
    public void onClick(View view) {
        usernameInputLayout.setErrorEnabled(false);
        errorMessage.setText("");
        passwordInputLayout.setErrorEnabled(false);

        if (loginValidation()) {
            singIn();
        }
    }

    //to move registration activity
    public void linkOnClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    //input validation
    private boolean loginValidation() {
        password = passwordInput.getText().toString().trim();
        username = usernameInput.getText().toString().trim();
        passwordInput.setText(password);
        usernameInput.setText(username);

        if (username.isEmpty()) {
            usernameInput.setError("please fill this field");
            return false;
        } else if (password.isEmpty()) {
            passwordInputLayout.setError("please fill this field");
            return false;

        } else if (password.length() > 30) {
            passwordInputLayout.setError("password is too long");
            return false;

        } else {
            return true;
        }
    }

    //to login
    private void singIn() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(username, "", password, "", "");
        Call<UserRegisterResponse> call = loginInterface.userLogin(userRegisterRequest);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if (response.isSuccessful()) {
                    DBHelper dbHelper = new DBHelper(LoginActivity.this);
                    UserRegisterResponse userRegisterResponse = response.body();
                    boolean result = dbHelper.saveUser(userRegisterResponse.getId(),
                            userRegisterResponse.getUsername(),
                            userRegisterResponse.getRole());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.USER_ID,userRegisterResponse.getId());
                    editor.commit();

                    if (result) {
                        Toast.makeText(LoginActivity.this, "CAN'T_SAVE_USER", Toast.LENGTH_SHORT).show();
                    } else if (userRegisterResponse.getRole().equals("user")) {
                        Intent intent = new Intent(LoginActivity.this, FuelStationActivity.class);
                        intent.putExtra("userId", userRegisterResponse.getId());
                        startActivity(intent);
                        finish();
                    } else if (userRegisterResponse.getRole().equals("Owner")) {
                        Intent intent = new Intent(LoginActivity.this, FuelStationListOwnerActivity.class);
                        intent.putExtra("userId", userRegisterResponse.getId());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    passwordInputLayout.setError(" ");
                    usernameInput.setError(" ");
                    errorMessage.setText("please check your credentials");
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "INTERNAL_SERVER_ERROR(CAN'T_REACH_SEVER)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}