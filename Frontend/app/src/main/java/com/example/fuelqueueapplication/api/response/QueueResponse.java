package com.example.fuelqueueapplication.api.response;

/**
 * queue response class
 * **/
public class QueueResponse {
    private String id;
    private String stationId;
    private String vehicleNumber;
    private String userId;
    private String pumpId;
    private String status;
    private String startingDateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPumpId() {
        return pumpId;
    }

    public void setPumpId(String pumpId) {
        this.pumpId = pumpId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(String startingDateTime) {
        this.startingDateTime = startingDateTime;
    }
}
