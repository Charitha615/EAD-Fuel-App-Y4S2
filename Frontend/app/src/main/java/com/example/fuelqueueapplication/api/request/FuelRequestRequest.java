package com.example.fuelqueueapplication.api.request;

/**
 * fuel request request class
 * **/
public class FuelRequestRequest {

    private String userId;
    private float noOfLiters;
    private String pumpId;
    private String approvalStatus;

    public FuelRequestRequest(String userId, float noOfLiters, String pumpId, String approvalStatus) {
        this.userId = userId;
        this.noOfLiters = noOfLiters;
        this.pumpId = pumpId;
        this.approvalStatus = approvalStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getNoOfLiters() {
        return noOfLiters;
    }

    public void setNoOfLiters(int noOfLiters) {
        this.noOfLiters = noOfLiters;
    }

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}


