package com.example.fuelqueueapplication.api.response;

/**
 * user register response class
 * **/
public class UserRegisterResponse {
    private String id;
    private String username;
    private String email;
    private String password;
    private String passwordKey;
    private String role;
    private String vehicleType;

    public UserRegisterResponse(String id, String username, String email, String password, String passwordKey, String role, String vehicleType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordKey = passwordKey;
        this.role = role;
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public String getRole() {
        return role;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
