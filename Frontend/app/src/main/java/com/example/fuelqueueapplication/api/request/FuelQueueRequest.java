package com.example.fuelqueueapplication.api.request;

/**
 * fuel queue request class
 * **/
public class FuelQueueRequest {
    private String StationId;
    private String VehicleNumber;
    private String UserId;
    private String PumpId;
    private String Status;
    private String StartingDateTime;

    public FuelQueueRequest(String stationId, String vehicleNumber, String userId, String pumpId,
                            String status, String startingDateTime) {
        StationId = stationId;
        VehicleNumber = vehicleNumber;
        UserId = userId;
        PumpId = pumpId;
        Status = status;
        StartingDateTime = startingDateTime;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getStationId() {
        return StationId;
    }

    public void setStationId(String stationId) {
        StationId = stationId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPumpId() {
        return PumpId;
    }

    public void setPumpId(String pumpId) {
        PumpId = pumpId;
    }
}
