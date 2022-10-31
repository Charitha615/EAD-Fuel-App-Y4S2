package com.example.fuelqueueapplication.api.response;

/**
 * fuel station response class
 * **/
public class FuelStationResponse {
    private String id;
    private String location;
    private int noOfPumps;
    private String availability;
    private String startingTime;
    private String endingTime;
    private String fuelType;

    public FuelStationResponse(String id, String location, int noOfPumps, String availability,
                               String startingTime, String endingTime, String fuelType) {
        this.id = id;
        this.location = location;
        this.noOfPumps = noOfPumps;
        this.availability = availability;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.fuelType = fuelType;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public int getNoOfPumps() {
        return noOfPumps;
    }

    public String getAvailability() {
        return availability;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public String getFuelType() {
        return fuelType;
    }
}
