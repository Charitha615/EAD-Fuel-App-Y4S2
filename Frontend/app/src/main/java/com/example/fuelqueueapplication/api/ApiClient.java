package com.example.fuelqueueapplication.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * api client class
 * **/
public class ApiClient {
    public static final String BASE_URL = "https://dotnetwebservice.azurewebsites.net";
    public static Retrofit retrofit = null;

    //get the retrofit client
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
