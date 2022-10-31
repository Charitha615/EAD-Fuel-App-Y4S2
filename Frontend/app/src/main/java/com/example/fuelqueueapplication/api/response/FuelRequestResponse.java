package com.example.fuelqueueapplication.api.response;

/**
 * fuel request response class
 * **/
public class FuelRequestResponse {
    private String id;
    private String userId;
    private float noOfLiters;
    private String pumpId;
    private String approvalStatus;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public float getNoOfLiters() {
        return noOfLiters;
    }

    public String getPumpId() {
        return pumpId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }
}
