package com.example.go4lunch.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;
    public static GoogleAPIService getApiService() {
        if(retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GoogleAPIService.class);
    }
}
