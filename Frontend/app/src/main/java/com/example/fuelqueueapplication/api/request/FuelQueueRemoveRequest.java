package com.example.fuelqueueapplication.api.request;

/**
 * fuel queue remove request class
 * **/
public class FuelQueueRemoveRequest {
    private String FuelAmount;
    private String EndDateTime;


    public FuelQueueRemoveRequest(String endDateTime, String fuelAmount) {
        EndDateTime = endDateTime;
        FuelAmount = fuelAmount;
    }
}
