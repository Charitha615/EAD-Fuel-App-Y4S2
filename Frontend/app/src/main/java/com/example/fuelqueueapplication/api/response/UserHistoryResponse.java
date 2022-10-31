package com.example.fuelqueueapplication.api.response;

/**
 * user history response class
 * **/
public class UserHistoryResponse {
    private String id;
    private String stationId;
    private String userId;
    private String startDateTime;
    private String endDateTime;
    private String fuelAmount;
    private String location;

    public UserHistoryResponse(String id, String stationId, String userId, String startDateTime,
                               String endDateTime, String fuelAmount, String location) {
        this.id = id;
        this.stationId = stationId;
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.fuelAmount = fuelAmount;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getStationId() {
        return stationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getFuelAmount() {
        return fuelAmount;
    }

    public String getLocation() {
        return location;
    }
}
