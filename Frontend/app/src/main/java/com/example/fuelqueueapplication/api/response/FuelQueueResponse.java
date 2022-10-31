package com.example.fuelqueueapplication.api.response;

/**
 * fuel queue response class
 * **/
public class FuelQueueResponse {
    private String id;
    private String stationId;
    private String vehicleNumber;
    private String user_id;
    private String pump_id;
    private String status;
    private String startingDateTime;

    public String getId() {
        return id;
    }

    public String getStationId() {
        return stationId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPump_id() {
        return pump_id;
    }

    public String getStatus() {
        return status;
    }

    public String getStartingDateTime() {
        return startingDateTime;
    }
}
