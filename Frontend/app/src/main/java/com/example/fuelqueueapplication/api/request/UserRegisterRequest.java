package com.example.fuelqueueapplication.api.request;

/**
 * user register request class
 * **/
public class UserRegisterRequest {
    private String Username;
    private String Email;
    private String Password;
    private String Role;
    private String VehicleType;

    public UserRegisterRequest(String username, String email, String password, String role, String vehicleType) {
        Username = username;
        Email = email;
        Password = password;
        Role = role;
        VehicleType = vehicleType;
    }

}
