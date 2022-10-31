package com.example.fuelqueueapplication.api.interfaces;
import com.example.fuelqueueapplication.api.request.ApprovalStatusUpdateRequest;
import com.example.fuelqueueapplication.api.request.FuelQueueRemoveRequest;
import com.example.fuelqueueapplication.api.request.FuelQueueRequest;
import com.example.fuelqueueapplication.api.request.FuelRequestRequest;
import com.example.fuelqueueapplication.api.request.StationTimeUpdateRequest;
import com.example.fuelqueueapplication.api.response.FuelQueueResponse;
import com.example.fuelqueueapplication.api.response.FuelRequestResponse;
import com.example.fuelqueueapplication.api.response.FuelStationDetailsResponse;
import com.example.fuelqueueapplication.api.response.FuelStationResponse;
import com.example.fuelqueueapplication.api.response.QueueResponse;
import com.example.fuelqueueapplication.api.response.UserHistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * fuel station interface
 * **/
public interface FuelStationInterface {
    //get all fuel station
    @GET("/api/FuelStation/getFuelStations")
    Call<List<FuelStationResponse>> getAllFuelStations();

    //get user history
    @GET("/api/FuelStation/getUserHistory/{id}")
    Call<List<UserHistoryResponse>> getUserHistory(@Path("id") String id);

    //get fuel station details
    @GET("/api/FuelStation/get/{id}")
    Call<FuelStationDetailsResponse> getFuelStationDetails(@Path("id") String id);

    //update fuel station time
    @PUT("/api/FuelStation/updateStartEndTime/{id}")
    Call<Void> updateStartingTimeEndTime(@Path("id") String id, @Body StationTimeUpdateRequest updateTime);

    //crete new fuel request
    @POST("/api/FuelStation/AddFuelRequest")
    Call<FuelRequestRequest> createRequest(@Body FuelRequestRequest fuelRequestRequest);

    //Enroll to the Queue
    @POST("/api/FuelStation/addFuelStationQueue")
    Call<FuelQueueResponse> createQueueRequest(@Body FuelQueueRequest fuelQueueRequest);

    //Get Queue list (GET)
    @GET("/api/FuelStation/getFuelQueue")
    Call<List<QueueResponse>> getQueueList();

    //Add to Queue (POST)
    @POST("/api/FuelStation/addFuelStationQueue")
    Call<Void> enrollToQueue(@Body FuelQueueRequest fuelQueueRequest);

    //Remove from the Queue. (DELETE)
    @HTTP(method = "DELETE", hasBody = true)
    Call<Void> fuelQueueRemove(@Url String url, @Body FuelQueueRemoveRequest fuelQueueRemoveRequest);

    //Get List of Request (GET)
    @GET("/api/FuelStation/getFuelRequests")
    Call<List<FuelRequestResponse>> getFuelRequest();

    //update fuel request status
    @PUT("/api/FuelStation/updateApprovalState/{id}")
    Call<Void> updateApprovalStatus(@Path("id") String id, @Body ApprovalStatusUpdateRequest approvalStatusUpdateRequest);
}
