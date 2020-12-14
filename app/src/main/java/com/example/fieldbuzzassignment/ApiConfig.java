package com.example.fieldbuzzassignment;

import com.sht.apimodels.APIInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    static APIInterface apiInterface;
    final static String BASE_URL = "https://recruitment.fisdev.com/";

    public static APIInterface getApiInstance(){
        if (apiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }
}
